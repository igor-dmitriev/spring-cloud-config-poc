Instructions

1. take a look at ApplicationTestWithCustomRepositoryTest
2. - run ConfigServer
   
   - run Client

   - get testKey's value:
curl localhost:8080/value 

   - update testKey's value in repository.properties file

   - refresh testKey property:
curl -d {} http://localhost:8080/refresh

   - get testKey's value again:
curl localhost:8080/value 

3. Watcher is not implemented


DOCS:
https://github.com/spring-cloud-incubator/spring-cloud-config-server-mongodb/commit/ecedae00e22978155606cf17ca1f2c3279769aff#diff-b109c993c15a6bd4f1aceeb3716c7a85 MongoDB Environment Repository

http://cloud.spring.io/spring-cloud-config/spring-cloud-config.html

http://burkond.blogspot.com/2016/08/using-spring-cloud-config-server.html

https://github.com/spring-cloud/spring-cloud-config/pull/287

http://kubecloud.io/guide-spring-cloud-config/