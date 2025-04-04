# 📍Desafio Desenvolvedor Android - Mercado Livre📍

## 🛠 Requisitos

- Android Studio Hedgehog ou superior
- JDK 17 ou superior
- Android SDK 26 ou superior


  📌 Arquitetura Utilizada

### Clean Architecture
O projeto é estruturado em três módulos:

#### 📱 App Module
- Apresentação (UI)
- ViewModels
- Composables
- Navegação
- DI Modules

#### 🧠 Domain Module
- Use Cases
- Models
- Repository Interfaces
- Business Rules

#### 💾 Data Module
- Repository Implementations
- Data Sources
- API Services
- DTOs e Mappers

### 📌 Padrões Implementados
- MVVM
- Repository Pattern para abstração de dados
- Use Cases para regras de negócio
- Princípios SOLID

## 📦 Tecnologias Utilizadas

### UI/UX
- **Jetpack Compose**: Framework de UI moderno
- **Material Design 3**: Sistema de design
- **Compose Navigation**: Navegação entre telas
- **Coil**: Carregamento e cache de imagens

### Arquitetura e DI
- **Hilt**: Injeção de dependência
- **ViewModel**: Gerenciamento de estado
- **StateFlow**: Fluxos de dados reativos

### Networking
- **Retrofit**: Cliente HTTP
- **Moshi**: Parsing JSON
- **OkHttp**: Interceptadores e logging

### Dados
- **Paging 3**: Carregamento paginado

  ### Testes
- **junit**
- **Espresso**
- **Coroutines Test**
- **Turbine**
- **MockK**

**Outros**
- **Coroutines**: para realizar chamadas assincronas
- **Room**: para guardar informações locais
- **Ktlint**: Formatação de código
- **LeakCanary**: Detecção de memory leaks


## 📱 Telas Implementadas
*   **Tela de Busca (Home):**
    *   Barra de pesquisa
    *   Skeleton para um carregamento dinâmico
    *   Listagem de produtos baseados na busca
*   **Tela de Detalhe do Produto:**
    *   Imagem do produto
    *   Informações do produto
    *   Listagem de últimos vistos

## 🚀 Considerações
A Api não estava funcionando, sempre me retornava com 403 mesmo com o token correto, então foi necessário ter um mock para preencher a listagem de itens. Por conta disso alguns códigos estão comentados e possui alguns avisos ao longo do código explicando o motivo.


## 💄 Screenshots

