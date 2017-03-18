package com.config.server;

import com.util.PropertyUtil;
import com.util.RepositoryPath;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.YamlProcessor;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static java.util.stream.Collectors.toList;

public class CustomEnvironmentRepository implements EnvironmentRepository {

  private static final String DEFAULT = "default";
  private static Log logger = LogFactory.getLog(CustomEnvironmentRepository.class);
  private MapFlattener mapFlattener = new MapFlattener();

  @Override
  public Environment findOne(String name, String profile, String label) {
    String[] profilesArr = StringUtils.commaDelimitedListToStringArray(profile);
    File file = new File(RepositoryPath.HARDCODED_PATH);
    Properties properties = PropertyUtil.loadProperties(file);
    logger.info("Properties loaded " + properties);
    List<CustomPropertySource> sources = properties.entrySet()
        .stream()
        .map(this::toCustomerPropertySource)
        .collect(toList());

    Environment environment = new Environment(name, profilesArr, label, "1.0", String.valueOf(file.lastModified()));
    for (CustomPropertySource propertySource : sources) {
      String sourceName = generateSourceName(name, propertySource);
      Map<String, Object> flatSource = mapFlattener.flatten(propertySource.getSource());
      PropertySource propSource = new PropertySource(sourceName, flatSource);
      environment.add(propSource);
    }
    return environment;
  }

  private CustomPropertySource toCustomerPropertySource(Map.Entry<Object, Object> props) {
    return new CustomPropertySource(props.getKey().toString(), props.getValue());
  }

  private String generateSourceName(String environmentName, CustomPropertySource source) {
    String sourceName;
    String profile = source.getProfile() != null ? source.getProfile() : DEFAULT;
    String label = source.getLabel();
    if (label != null) {
      sourceName = String.format("%s-%s-%s", environmentName, profile, label);
    } else {
      sourceName = String.format("%s-%s", environmentName, profile);
    }
    return sourceName;
  }

  public static class CustomPropertySource {

    private String profile;
    private String label;
    private Map<String, Object> source = new LinkedHashMap<>();

    public CustomPropertySource(String key, Object value) {
      source.put(key, value);
    }

    public String getProfile() {
      return profile;
    }

    public void setProfile(String profile) {
      this.profile = profile;
    }

    public String getLabel() {
      return label;
    }

    public void setLabel(String label) {
      this.label = label;
    }

    public Map<String, Object> getSource() {
      return source;
    }

    public void setSource(Map<String, Object> source) {
      this.source = source;
    }

  }

  private static class MapFlattener extends YamlProcessor {

    public Map<String, Object> flatten(Map<String, Object> source) {
      return getFlattenedMap(source);
    }
  }

}
