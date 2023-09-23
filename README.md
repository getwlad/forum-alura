# Challenge Fórum Alura
## :world_map: Índice

- [Sobre o Projeto](#information_source-sobre-o-projeto)
- [Funcionalidades](#hammer-funcionalidades-do-projeto)
- [Documentação](#floppy_disk-documentação)
- [Instalação](#hammer_and_wrench-instalação)
- [Sprints](#bulb-sprints)
- [Tecnologias e Recursos](#open_book-tecnologias-e-recursos-utilizados)
- [Pessoas Desenvolvedoras do Projeto](#smile-autores)
## :information_source: Sobre o projeto
![Badge em Desenvolvimento](http://img.shields.io/static/v1?label=STATUS&message=CONCLUÍDO&color=GREEN&style=for-the-badge)<br>
Este projeto é a resolução de um desafio proposto na formação Java oferecida pela Oracle em parceria com a alura, o <br>
objetivo é fazer uma API utilizando o Spring Boot.
## :hammer: Funcionalidades e recursos do projeto

- Permite criar, obter, listar, atualizar e excluir um tópico;
- Permite criar e autenticar um usuário via JWT com Spring Security;
- Autenticação via JWT;
- Testes automatizados;
- Documentação com Spring Doc.
- Banco de dados para armazenar todos os dados.
  
## :floppy_disk: Documentação
A documentação está disponível na url `seuservidor/swagger-ui/index.html`
![Web 1](https://github.com/getwlad/assets/blob/main/docForumAlura.png)

## :hammer_and_wrench: Instalação
Você pode obter uma cópia do projeto através do comando
```bash
git@github.com:getwlad/forum-alura.git
```
Utilize uma IDE compatível com java para executar a aplicação a partir da classe ForumAluraApplication.

No arquivo `application.properties` deve-se configurar o banco de dados, , neste projeto foi utilizado o MySql.<br>
É importante também configurar o banco de dados para testes no arquivo `application-test.properties`. <br>
As migrations serão executadas assim que iniciar o projeto. <br>

Diagrama do banco de dados: <br>
![Web 1](https://github.com/getwlad/assets/blob/main/diagramaForumAl.png)

## :bulb: Sprints

A implementação do projeto se deu por 8 sprints:

| Sprint     | Descrição                                                          |
| :--------- | :----------------------------------------------------------------- |
| `Sprint 1` | Crie seu repositório de projetos no GitHub.    |
| `Sprint 2` | Diagrama do Banco de Dados.                      |
| `Sprint 3` | Implementação de autenticação.           |
| `Sprint 4` | Cadastro de um novo tópico.    |
| `Sprint 5` | Listagem de tópicos.    |
| `Sprint 6` | Detalhamento de tópicos. |
| `Sprint 7` | Exclusão de tópico. |
| `Sprint 8` | Atualização de tópico. |
| `Sprint Feature Extra` | Adição de respostas a tópicos. |

## :open_book: Tecnologias e Recursos Utilizados

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/pt-BR/)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)
[![Trello](https://img.shields.io/badge/Trello-0052CC?style=for-the-badge&logo=trello&logoColor=white)](https://trello.com/)
[![Git](https://img.shields.io/badge/GIT-E44C30?&style=for-the-badge&logo=git&logoColor=white)](https://git-scm.com/doc)
[![GitHub](https://img.shields.io/badge/GitHub-100000?&style=for-the-badge&logo=github&logoColor=white)](https://github.com/)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)


## :smile: Autores
[<img loading="lazy" src="https://avatars.githubusercontent.com/u/102919718?v=4" width=115><br><sub>Wladmir Rodriuges</sub>](https://github.com/getwlad) 
