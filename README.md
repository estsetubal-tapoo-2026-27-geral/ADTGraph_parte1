# A3.1 — Introdução aos grafos

Projeto de apoio aos slides `A3.1_Introducao_aos_Grafos_26_27_v3.md`.

## Atividade: o que devem fazer

1. Explorem as classes fornecidas e executem `Main`.
2. Completem **`hasDirectConnection`** em `MetroNetworkImpl` e ativem as chamadas comentadas em `Main`.
3. Acrescentem **uma única funcionalidade: `directTravelMinutes`**, à interface e à implementação.
4. Ajustem `Main` para demonstrar os resultados e tratar as exceções indicadas abaixo.

Não é pedido o planeamento de outra aplicação nesta atividade.

## Ferramentas e ficheiros disponibilizados

- Java 17 ou superior e Maven, ou um IDE com suporte Maven.
- Biblioteca SmartGraph 2.2.0, declarada em `pom.xml`; utilizem `Graph`, `Vertex`, `Edge` e `GraphEdgeList`.
- Podem utilizar `List` e `ArrayList` para reunir os nomes dos vizinhos e `Locale.ROOT` para normalizar nomes.
- Não é necessário implementar o ADT Graph ou estudar a sua estrutura interna nesta aula.

| Ficheiro | Responsabilidade | Estado inicial |
| --- | --- | --- |
| `Station.java` | Nome da estação, construtor, `getName()` e `toString()` | Fornecido |
| `Connection.java` | Tempo da ligação, construtor, `getTravelMinutes()` e `toString()` | Fornecido |
| `InvalidMetroException.java` | Exceção do domínio com mensagem | Fornecido |
| `MetroNetwork.java` | Interface da aplicação | Acrescentar a nova assinatura |
| `MetroNetworkImpl.java` | Utilização do ADT e regras do domínio | Completar `hasDirectConnection` e acrescentar o novo método |
| `Main.java` | Rede fictícia, consultas e tratamento de erros | Ativar e acrescentar demonstrações |

Os ficheiros Java estão em `src/main/java/tapoo/metro`.

## Executar

Abram a pasta que contém `pom.xml` como projeto Maven e executem `tapoo.metro.Main` no IDE.
Na linha de comandos, a partir dessa pasta:

```bash
mvn compile exec:java
```

A primeira execução necessita de acesso à Internet para obter as dependências Maven.
O projeto inicial compila e executa: as chamadas a métodos ainda por implementar estão comentadas.
`hasDirectConnection` lança `UnsupportedOperationException` enquanto o TODO não for resolvido.

Resultado inicial, com a ordem dos vizinhos variável:

```text
Stations: 5
Alameda neighbours: [ARROIOS, SALDANHA]
Aeroporto neighbours: []
Cannot add station: Station already exists.
Cannot create connection: Unknown station: UNKNOWN
```

## Modelo e regras do domínio

```java
Graph<Station, Connection> graph = new GraphEdgeList<>();
```

- Vértices guardam objetos `Station`; arestas guardam objetos `Connection`.
- A rede é não orientada, sem laços e sem ligações paralelas.
- O tempo estimado é positivo, expresso em minutos e igual nos dois sentidos.
- Nomes nulos ou vazios após `trim()` são inválidos.
- Todos os nomes são normalizados com `trim().toUpperCase(Locale.ROOT)` antes do registo e da pesquisa.
- `"Alameda"`, `" alameda "` e `"ALAMEDA"` identificam a mesma estação; o nome guardado é `"ALAMEDA"`.
- O registo rejeita nomes repetidos. As consultas e ligações exigem estações existentes.
- `MetroNetworkImpl` lança `InvalidMetroException` para violações destas regras.
- Uma estação isolada tem uma coleção de vizinhos vazia; a ordem dos vizinhos não é garantida.

`Station` e `Connection` mantêm a igualdade por identidade: não redefinem `equals` ou `hashCode`.
A aplicação verifica explicitamente a unicidade dos nomes. Ligações diferentes podem ter o mesmo
valor de `travelMinutes`: cada inserção cria um objeto `Connection` distinto.
A validação do domínio é centralizada em `MetroNetworkImpl`.

### Objetos e referências

`Station` é o objeto do domínio. `Vertex<Station>` é a referência ao vértice que o guarda.
`vertex.element().getName()` obtém o nome da estação.
`Edge<Connection, Station>` identifica uma aresta; `edge.element().getTravelMinutes()` obtém o seu tempo.

| Método do domínio | Operações do ADT |
| --- | --- |
| `addStation` | `vertices`, `element`, `insertVertex` |
| `connectStations` | localizar vértices, `areAdjacent`, `insertEdge` |
| `hasDirectConnection` | localizar vértices, `areAdjacent` |
| `neighbouringStations` | localizar vértice, `incidentEdges`, `opposite`, `element` |
| `numberOfStations` | `numVertices` |
| `directTravelMinutes` (a acrescentar) | localizar vértices, `incidentEdges`, `opposite`, `element` |

`findStation` recebe um nome já normalizado e devolve o vértice ou `null`.
`requireStation` valida e normaliza o nome, pesquisa a estação e lança a exceção se não existir.

## 1. Explorar a solução

Leiam `Station`, `Connection` e `MetroNetwork`. Executem `Main` e analisem `MetroNetworkImpl`.
A rede fornecida contém estas ligações fictícias:

| Estações | Minutos |
| --- | --- |
| Alameda–Arroios | 2 |
| Alameda–Saldanha | 3 |
| Arroios–Campo Grande | 3 |
| Saldanha–Campo Grande | 2 |

Aeroporto é uma estação isolada. Existe um ciclo e há um percurso de Alameda a Campo Grande,
mas estas duas estações não têm ligação direta.

## 2. Completar `hasDirectConnection`

Mantenham a assinatura da interface. Validem e localizem os dois vértices antes de usar `areAdjacent`.
Podem reutilizar `requireStation`. Substituam o lançamento de `UnsupportedOperationException` pela implementação.
Depois, ativem as três chamadas comentadas em `Main`.

| Consulta | Resultado esperado |
| --- | --- |
| Alameda–Arroios | `true` |
| Arroios–Alameda | `true` |
| Alameda–Campo Grande | `false`, apesar de existir um percurso |
| Alameda–Aeroporto | `false` |
| Alameda–Alameda | `false`, pois não existem laços |
| Nome inexistente, vazio ou `null` | `InvalidMetroException` |

## 3. Acrescentar o tempo de uma ligação direta

Acrescentem a assinatura a `MetroNetwork` e implementem-na em `MetroNetworkImpl`:

```java
int directTravelMinutes(String origin, String destination)
    throws InvalidMetroException;
```

**Contrato:** devolver o tempo da ligação direta; lançar `InvalidMetroException` para nomes inválidos,
estações inexistentes ou ausência de ligação direta, incluindo a consulta da estação a si própria.
O resultado é igual nos dois sentidos. Não é pedido um algoritmo de caminho mais curto.

Usem `incidentEdges`, `opposite` e `element()` para encontrar a ligação e obter `getTravelMinutes()`.
Ativem as duas chamadas preparadas em `Main` e acrescentem os restantes casos, tratando as exceções
individualmente para a execução poder continuar.

| Consulta | Resultado esperado |
| --- | --- |
| Alameda–Arroios | `2` |
| Arroios–Alameda | `2` |
| `" alameda "`–`"ARROIOS"` | `2` |
| Alameda–Campo Grande | Exceção, mesmo existindo um percurso |
| Alameda–Aeroporto | Exceção |
| Nome inexistente, vazio ou `null` | Exceção |
| Alameda–Alameda | Exceção |

## Critérios de conclusão

- [ ] O modelo usa `Station` e `Connection`.
- [ ] `hasDirectConnection` está completo e as chamadas correspondentes estão ativas.
- [ ] `directTravelMinutes` está declarado na interface e implementado na classe.
- [ ] `Main` demonstra os dois sentidos, normalização dos nomes e todos os casos de erro pedidos.
- [ ] Estações isoladas e ligações distintas com tempos iguais funcionam.
- [ ] Os elementos do grupo explicam a diferença entre um objeto do domínio e uma referência do ADT.

## Documentação do ADT Graph

Consultem o Javadoc da biblioteca SmartGraph indicado nos slides:

- [Interface Graph](https://javadoc.io/doc/com.brunomnsilva/smartgraph/latest/com/brunomnsilva/smartgraph/graph/Graph.html): operações do grafo não orientado utilizado neste projeto.
- [Interface Digraph](https://javadoc.io/doc/com.brunomnsilva/smartgraph/latest/com/brunomnsilva/smartgraph/graph/Digraph.html): referência para grafos orientados.

Utilizem a documentação para consultar as assinaturas, os resultados e as exceções das operações do ADT.
A implementação interna do ADT será estudada na aula A3.4.
