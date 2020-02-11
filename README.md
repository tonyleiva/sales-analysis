# Teste para Desenvolvedor - Ilegra

Desenvolvimento de uma aplicação Java para análise e processamento de dados contidos dentro de arquivos. Os arquivos serão lidos a partir do diretório.

```bash
%HOMEPATH%/[inputPath]
```

Onde por padrão [inputPath]='/data/in'. Mas este pode ser alterado no arquivo resources/application.yml.

Para cada arquivo de entrada, será criado um arquivo de saída com o nome do arquivo de entrada, mas com '.done' adicionado antes da extensão. Por exemplo, para o arquivo de entrada 'file1.dat' o arquivo de saída terá como nome 'file1.done.dat'.

Os arquivos com o resultado da análise serão salvos no diretório:

```bash
%HOMEPATH%/[outputPath]
```

Onde por padrão [outputPath]='/data/out'. Mas este pode ser alterado no arquivo resources/application.yml.

Também podem ser alterados os valores padrão para as seguintes propriedades:

```bash
file:
  extension: ".dat"

delimiter:
  lineProperties: "ç"
  items: ","
  itemProperties: "-"
```

Onde:

'file > extension' é o format de arquivo a ser processado.

'delimiter > lineProperties' é o separador das propriedades de uma linha nos arquivos a serem processados.

'delimiter > items' é o separador dos itens na entrada de uma venda

'delimiter > itemProperties' é o separados das propriedades de cada item na entrada de uma venda.


## Para executar os testes
```bash
mvn test
```

## Para compilar e criar o arquivo jar executável
```bash
mvn package
```

## Para executar o programa 
```bash
java -jar target/sales-analysis-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

Ou executar o jar já compilado na raiz do projeto:

```bash
java -jar sales-analysis.jar
```