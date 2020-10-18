O projeto tem como objetivo ser um web service rest para realizar a conversão de valores entre as moedas BRL, USD, EUR e JPY. Para a criação do projeto foi usada a linguagem de programação JAVA juntamento com os frameworks Spring e JPA. Importante dizer também o uso do Maven versão 3.6.3. O uso da linguagem e dos frameworks foi feito primeiramente pelo bom conhecimento do programador, mas também por fornecer algumas vantagens.

No caso do JPA o uso foi para facilitar o acesso ao banco de dados. As entidades são mapeadas com o uso de anotações e todas as operações em banco já tem a criação da query facilitada. Caso alguma coluna seja criada por exemplo, basta apenas criar o novo atributo na classe anotada com @Entity e anotar o atributo com @Column passando o nome da coluna.

Já o framework Spring auxilia em várias partes do projeto. Faz a injeção de dependência, faz a definição de camadas das entidades, controle de transação e auxilia na controller disponibilizando os endpoints.

O Projeto tem as seguintes divisões de pastas:
  - Controller, onde ficam os controllers que disponobilizam os serviços.
  - Service, onde fica a parte de regra de negócio, realiza as validações, faz chamadas de classes utilitárias e de acesso a banco.
  - DAO, onde ficam as classes realizam o acesso ao banco de dados.
  - Util, Classes utilitárias.
  - Entity, onde ficam as classes que representam entidades do banco de dados.
  - Exception, classes de auxiliam o controle de exceção.
  
Como rodar a aplicação:
 1) Faça download de um servidor web, no meu caso utilizei Tomcat versão 9.
 2) Utilizei a versão 8 do Java, recomendo utiliza a mesma para não acontecer problemas de compatibiladade com os frameworks.
 2) Baixe o projeto do github.
 3) Gere um arquivo war do projeto, eu fiz isso utilizando o eclipse.
 4) Jogue o war na pasta webapps do tomcat.
 5) De start no servidor.
 
Endpoints:
  A base da url do projeto quando o servidor estiver no ar no ambiente local é http://localhost:8080/ExchangeRestApi
  
  1)Listagem de operações de um usuário
    http://localhost:8080/ExchangeRestApi/exchange/{chave}
    Chamada do tipo GET.
    Irá devolver todas as operações de um determinado usuário.
    
  2)Conversão de moedas
    http://localhost:8080/ExchangeRestApi/exchange
    Chamada do tipo POST
    Passar como parametro no body um json como o exemplo abaixo:
    {"userId":"AAAA","currencySource":"BRL","valueSource":100,"currencyTarget":"BRL"}
    Importante também colocar no header o seguinte parâmetro: Content-Type: application/json
