 
<p align="center">
  <img src="https://vagas.byintera.com/wp-content/uploads/2021/04/luiza-labs.1616501197-1024x376.png" alt="logo" width="150" />
</p>
<h1 align="center">Order parser</h1>
<p align="center">
 <b>Backend para resolucao do desafio por Laís Soares de Carvalho</b></br>
</p>

# Sumário

- [Requisitos](#Requisitos) 
- [Configuração de Ambiente](#Configuração-de-Ambiente)
- [Testes](#Testes)
- [Variaveis de ambiente](#Variaveis de ambiente)
- [Sonar](#Sonar)


# Requisitos

[Java 21](https://www.oracle.com/br/java/technologies/downloads/#java21)

[Docker 26.0.2](https://docs.docker.com/engine/release-notes/26.0/#2602)

[Docker-Compose 2.27.0](https://docs.docker.com/compose/release-notes/#2270)

---
# Variaveis de ambiente

MYSQL_URL: jdbc:mysql://localhost:3306/dbparser?allowPublicKeyRetrieval=true&useSSL=false

USERNAME:admin

PASSWORD:admin

---
# Sonar
```
mvn sonar:sonar -Dsonar.projectKey=order-parser -Dsonar.projectName=order-parser -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.exclusions='**/target/**,**/src/main/resources/**,**/src/test/java/**,**/*Config.*,**/*Entity.*,**/entities/**,**/*Mapper.*,**/dto/**,**/model/**,**Application.*' -Dsonar.sourceEncoding=UTF-8 -Dsonar.java.binaries=target 
```

---
# Swagger
```
http://localhost:8080/swagger-ui/index.html
```