# Template geral para prova de API

Esta branch é uma base reutilizável para provas com Spring Boot, PostgreSQL, testes, Docker, GitHub Actions e deploy na EC2. A API de transações é apenas um exemplo funcional: durante a prova, adapte ou substitua o domínio `transacoes` sem alterar a infraestrutura.

## O que já está pronto

- PostgreSQL em Docker e configuração por variáveis de ambiente.
- GitHub Actions: testes em push/PR e deploy após merge na `main`.
- Secrets para host, chave SSH, banco e API externa.
- Modelos de entidade, repositório, serviço e teste em `templates/`.

## Adaptação rápida

1. Copie um modelo de `templates/java` e implemente o domínio do enunciado.
2. Adicione DTOs e controller para as rotas pedidas.
3. Copie o modelo de teste e cubra as regras de negócio.
4. Preserve `Dockerfile`, `docker-compose.yml` e `.github/workflows/ci.yml`.
5. Abra uma PR para `main`; testes executam antes do merge e o deploy ocorre após o merge.

Leia [o guia de adaptação](docs/COMO_ADAPTAR.md).
