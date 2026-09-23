# API de Tarefas

Projeto-base para uma prova de API REST com Spring Boot, PostgreSQL, testes e deploy automatizado.

## API

| Método | Rota | Ação |
| --- | --- | --- |
| POST | `/tasks` | Cria uma tarefa |
| GET | `/tasks` | Lista tarefas |
| GET | `/tasks/{id}` | Busca uma tarefa |
| PUT | `/tasks/{id}` | Atualiza uma tarefa |
| DELETE | `/tasks/{id}` | Remove uma tarefa |

Exemplo de criação:

```json
{
  "title": "Estudar Spring Boot",
  "description": "Revisar controllers e testes"
}
```

## Testes

- `TaskServiceTest`: testes unitários com repositório simulado.
- `TaskRepositoryIT`: teste de integração com um PostgreSQL real.

O comando `mvn verify` executa os dois grupos. No GitHub Actions, o PostgreSQL é iniciado como serviço temporário.

## Deploy na EC2

O workflow `.github/workflows/ci.yml` executa os testes em todo push e pull request. Em um push na `main`, após os testes passarem, transfere o projeto para a EC2 e executa Docker Compose. A API ficará na porta `8082` da EC2; o PostgreSQL fica isolado na rede Docker.

Antes do primeiro deploy, com o GitHub CLI autenticado, execute:

```powershell
.\scripts\bootstrap-aws.ps1 -GitHubRepository "SEU_USUARIO/prova-intermediaria"
```

O script instala Docker, prepara o diretório da EC2 e configura os secrets `HOST_TEST`, `KEY_TEST` e `DB_PASSWORD` no GitHub. Ele não abre portas no firewall.
