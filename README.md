### Journal Notice

Aplicação para objeto de estudo desenvolvida utilizando o framework <a href="https://www.dropwizard.io/en/latest/">dropwizard</a>, a aplicação é desenvolvida em três módulos, captura, busca e envio. Para busca é feito a raspagem de dados de um diário público para armazenar as publicações no banco relacional e Elasticsearch, sendo que este roda em um Job definido pela *cron* nas configurações. Para busca é feito a procura com o Elasticsearch em cima das publicações armazenadas pela captura, os resultados possuem as palavras-chave procuradas grifadas. Para o envio é feito o envio do e-mail com as palavras-chave configurados do usuário no banco PostgreSQL.

### Usage

Subindo os containers necessários para a aplicação:
<pre><code>docker-compose up -d</code></pre>
Rodando os testes de forma local:
<pre><code>mvn test</code></pre>
Para acessar os endpoints pelo *swagger*: http://localhost:8080/v1/swagger

**OBS:** Para utilizar o envio é necessário criar uma conta no Sendgrid e alterar a chave no *dev-config.yml*

### Tecnologias

- Java 11
- Dropwizard
- PostgreSQL
- Elasticsearch
- Sendgrid
- Swagger


