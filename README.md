[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=tonyleiva_sales-analysis&metric=alert_status)](https://sonarcloud.io/dashboard?id=tonyleiva_sales-analysis)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=tonyleiva_sales-analysis&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=tonyleiva_sales-analysis)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=tonyleiva_sales-analysis&metric=security_rating)](https://sonarcloud.io/dashboard?id=tonyleiva_sales-analysis)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=tonyleiva_sales-analysis&metric=code_smells)](https://sonarcloud.io/dashboard?id=tonyleiva_sales-analysis)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=tonyleiva_sales-analysis&metric=coverage)](https://sonarcloud.io/dashboard?id=tonyleiva_sales-analysis)

# Sales Analysis

Desenvolvimento de uma aplicação Java para análise e processamento de dados contidos dentro de arquivos. Os arquivos serão lidos a partir do diretório.

```bash
%HOMEPATH%/[input]
```

Onde por padrão [input]=```/data/in```. Mas este pode ser alterado no arquivo resources/application.yml.

Para cada arquivo de entrada, será criado um arquivo de saída com o nome do arquivo de entrada, mas com '-done' adicionado antes da extensão. Por exemplo, para o arquivo de entrada 'file1.dat' o arquivo de saída terá como nome 'file1-done.dat'.

Os arquivos com o resultado da análise serão salvos no diretório:

```bash
%HOMEPATH%/[output]
```

Onde por padrão [output]=```/data/out```. Mas este pode ser alterado no arquivo resources/application.yml.

Também podem ser alterados os valores padrão para as seguintes propriedades:

```bash
app:
  directory:
    input: "/data/in" #diretório de entrada
    output: "/data/out" #diretório de saída
  file:
    extension: ".dat" #extensão dos arquivos a ser processados
```


## Testes

Para executar os testes, a partir da raíz do projeto, executar um dos seguintes comandos:

```bash
./gradlew test 

./gradlew test jacocoTestReport #para gerar o relatório do JaCoCo
```
- O relatório XML será gerado em ```build/reports/jacoco/test/jacocoTestReport.xml```
- O relatório HTML será gerado em ```build/reports/jacoco/test/html/index.html```

## Compilar e criar arquivo jar executável
```bash
./gradlew build
```
- O arquivo JAR será gerado em ```build/libs```

## Executar
```bash
java -jar build/libs/sales-analysis-0.0.1-SNAPSHOT.jar
```
