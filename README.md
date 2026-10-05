# A3.1 - Introdução aos grafos

## Objetivo e entregas

**Em 40 minutos, analisem a solução do metro e planeiem outra aplicação.**
Entreguem `documents/ATIVIDADE.md` preenchido, o desenho do grafo e um ficheiro
Java com a interface do domínio. A implementação da nova aplicação fica fora
desta atividade. A solução do metro está completa e não contém métodos por fazer.

## Ferramentas e código disponibilizado

- Java 17 ou superior. 
- `MetroNetwork` define o comportamento do domínio.
- `MetroNetworkImpl` utiliza `Graph<String,Integer>`; consultar a documentação Javadoc em [`docs/graph-javadoc/Graph.html`](docs/graph-javadoc/Graph.html).
- `Main` demonstra uma rede fictícia e o tratamento de um erro.

## Executar o exemplo

No IDE, executem `tapoo.metro.Main`. Com Maven:

```bash
mvn package
java -jar target/adt-graph-metro-1.0.0.jar
```

Alternativa com o JDK, sem Maven (na raiz do projeto, Bash):

```bash
mkdir -p out
javac -encoding UTF-8 -d out $(find src/main/java -name '*.java')
java -cp out tapoo.metro.Main
```

Resultado esperado (a ordem dos vizinhos não faz parte do contrato):

```text
Stations: 6
Central neighbours: [Museum, Garden]
Airport neighbours: []
Central - Museum: true
Museum - Central: true
Central - University: false
Cannot create connection: Unknown station: Unknown
```

## 1. Analisar a solução do metro (8 minutos)

Leiam, por esta ordem, `MetroNetwork.java`, `Main.java` e `MetroNetworkImpl.java`.
Não é necessário estudar a implementação interna de `GraphEdgeList` nesta aula.

### Modelo adotado

Cada vértice guarda o nome de uma estação, único e sensível a maiúsculas.
Cada aresta guarda um tempo de viagem positivo, em minutos. A rede é não orientada,
sem laços e com uma única ligação por par de estações. Os tempos são fictícios e
iguais nos dois sentidos. O aeroporto é um exemplo de vértice isolado.

As ligações Central-Museum-University-Garden-Central formam um ciclo e permitem
caminhos alternativos. A consulta `hasDirectConnection` verifica apenas uma
ligação direta. Não calcula um percurso entre estações.

| Necessidade | Método do domínio | Operações do ADT |
|---|---|---|
| Criar estação | `addStation` | `vertices`, `element`, `insertVertex` |
| Criar ligação | `connectStations` | localizar estações, `areAdjacent`, `insertEdge` |
| Verificar ligação direta | `hasDirectConnection` | localizar estações, `areAdjacent` |
| Obter estações vizinhas | `neighbouringStations` | localizar estação, `incidentEdges`, `opposite`, `element` |
| Contar estações | `numberOfStations` | `numVertices` |

`findStation` percorre `vertices()` e compara o nome com `element()`. Esta pesquisa
é uma responsabilidade da aplicação. O ADT identifica vértices e arestas por
referências `Vertex<V>` e `Edge<E,V>`.

Questões para o grupo:

1. Porque são usados `String` nos vértices e `Integer` nas arestas?
2. Como se obtêm os nomes dos vizinhos a partir das arestas incidentes?
3. Porque devolve `false` a consulta Central-University, apesar de existir um percurso?
4. Onde se impedem nomes repetidos ?

### Contratos e erros

A interface rejeita nomes nulos/vazios, estações desconhecidas, duplicados,
ligações de uma estação a si própria e tempos não positivos com
`IllegalArgumentException`. Ignora espaços exteriores nos nomes. As consultas
de vizinhos devolvem uma coleção vazia para uma estação isolada.

## 2. Escolher uma aplicação e definir o modelo (12 minutos)

| Aplicação | Vértices possíveis | Arestas possíveis | Ideias de operações |
|---|---|---|---|
| Rede social | Utilizadores | Seguir outro utilizador | adicionar utilizador, seguir, listar seguidores, contar pessoas seguidas |
| Rede de voos | Aeroportos | Voos diretos | adicionar aeroporto, criar voo, listar destinos diretos, verificar voo direto |
| Distribuição de água | Reservatórios e pontos de distribuição | Condutas | adicionar ponto, ligar pontos, listar condutas, remover conduta |
| Rede informática | Computadores, servidores e routers | Ligações de comunicação | adicionar dispositivo, criar ligação, listar vizinhos, contar ligações |
| Plano de estudos | Unidades curriculares | Pré-requisitos | adicionar UC, definir pré-requisito, listar pré-requisitos diretos, listar dependências diretas |
| Mapa de um jogo | Salas ou locais | Passagens | adicionar local, criar passagem, listar locais acessíveis diretamente, fechar passagem |

Não existe um único modelo correto. Expliquem o significado de cada aresta e
justifiquem orientação, pesos e arestas paralelas. Para um pré-requisito, indiquem
explicitamente o sentido, por exemplo `Programming I -> Programming II`.

**Modelos orientados:** o código fornecido implementa apenas o caso não orientado.
Podem planear uma aplicação orientada, mas devem distinguir ligações de entrada
e de saída. Nesse caso, proponham extensões como `incomingEdges(v)` e
`outgoingEdges(v)` e indiquem a origem e o destino de cada ligação. Não usem
`incidentEdges(v)` seguido de `opposite(v,e)` como se todos os extremos fossem
destinos acessíveis. Não é necessário implementar estas extensões.

## 3. Definir a interface e mapear operações (15 minutos)

Preencham o modelo em `docs/ATIVIDADE.md`:


### Critérios de conclusão

- [ ] O problema e as perguntas estão claros.
- [ ] Vértices, arestas e dados correspondem ao problema.
- [ ] Orientação, pesos e arestas paralelas têm uma justificação.
- [ ] O desenho contém cinco a sete vértices e corresponde ao modelo.
- [ ] A interface contém quatro ou cinco operações do domínio.
- [ ] Cada operação tem contrato e correspondência com o ADT.
- [ ] O exemplo de chamadas inclui resultados esperados.
- [ ] As necessidades adicionais de um grafo orientado estão identificadas, se aplicável.

## Referência do ADT Graph

A documentação Javadoc da interface `Graph` está disponível em [`docs/graph-javadoc/Graph.html`](docs/graph-javadoc/Graph.html).
O ficheiro fonte correspondente está em [`docs/graph-javadoc/Graph.java`](docs/graph-javadoc/Graph.java).
A implementação fornecida aceita arestas paralelas e rejeita laços. A aplicação do metro acrescenta a regra
que impede ligações paralelas. A representação interna será tema da aula A3.4.

