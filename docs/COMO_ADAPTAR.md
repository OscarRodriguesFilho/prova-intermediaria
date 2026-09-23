# Guia de adaptação rápida

Para cada tabela nova, crie entidade JPA, repositório, DTOs, serviço, controller e testes. Use GitHub Secrets para senhas, IPs e chaves.

`ServiceTest` valida regras com repositórios simulados. `RepositoryIT` valida persistência com PostgreSQL real no GitHub Actions.
