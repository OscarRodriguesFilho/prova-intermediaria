# API de Produtos

API REST para produtos de uma loja, com PostgreSQL, auditoria e alerta de estoque usando o padrão Observable.

## Rotas

| Método | Rota | Ação |
| --- | --- | --- |
| POST | `/produtos` | Cria um produto |
| GET | `/produtos` | Lista produtos |
| GET | `/produtos/{id}` | Busca um produto por id |
| DELETE | `/produtos/{id}` | Exclui um produto |

Exemplo para criar um produto:

```json
{
  "nome": "Caderno",
  "descricao": "Caderno universitário",
  "preco": 19.90,
  "quantidade": 5
}
```

## Observable

O `ProdutoEventPublisher` é o sujeito observado. Ao criar ou excluir um produto, ele publica um `ProdutoEvent` para os observers registrados pelo Spring:

- `AuditObserver`: grava na tabela `audit_event` o id do produto, o instante e a operação `CREATE` ou `DELETE`.
- `LowStockObserver`: ao receber um evento `CREATE` cuja quantidade é menor que 10, registra um alerta de estoque baixo no log da aplicação.

## Testes e cobertura

`ProdutoServiceIT` é um teste de integração da camada de serviço. Ele usa PostgreSQL, cria e exclui um produto, e valida os dois eventos de auditoria persistidos.

Execute localmente com um PostgreSQL disponível e as variáveis `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER` e `DB_PASSWORD` configuradas:

```powershell
mvn verify
```

O JaCoCo gera o relatório em `target/site/jacoco/index.html`. O GitHub Actions inicia um PostgreSQL temporário e executa `mvn verify` em todo push e pull request.

## Deploy

Em um push na `main`, o GitHub Actions roda os testes e, se todos passarem, envia o projeto para a EC2 e inicia API e PostgreSQL com Docker Compose. A API publica a porta 8080:

`http://ec2-44-197-175-105.compute-1.amazonaws.com:8080/produtos`

Os secrets usados pelo workflow são `HOST_TEST`, `KEY_TEST` e `DB_PASSWORD`. Eles ficam em **Settings > Secrets and variables > Actions** do repositório, mas seus valores não podem ser visualizados depois de salvos.
