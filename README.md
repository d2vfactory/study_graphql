# study_graphql

## Dependencies
- graphql-spring-boot 5.0.2
    - endpoint 및 config 자동 적용
- graphiql-spring-boot 3.6.0
    - graphql 요청 웹 UI 제공
- graphql-java-tools 5.2.0
    - resolver 제공

## DB
- h2db 
    - (app test모드) jdbc:h2:file:~/test
    - (app test모드) jdbc:h2:mem:~/test
    - (db 실행모드) jdbc:h2:tcp://localhost/~/test  
        - 설치파일 : http://www.h2database.com/html/main.html

## GraphQL 구현 방법
- Query
    - 조회 명령 처리 (CRUD의 R)
    - GraphQLQueryResolver를 상속받아 구현
- Mutation
    - 추가/수정/삭제 처리 (CRUD의 CUD)  
    - GraphQLMutationResolver를 상속받아 구현    

    
## Sample Entity
### User
```
    id, name, age
```
### Product
```
    id, name, price
```
### Purchase, PurchaseDTO
```
    id, date, product, user
```

## GraphQL Schemas
```
    # product
    type Product {
        id: Long!
        name: String!
        price: Int!
    }
    
    type Query {
        products: [Product]!
        product(id: Long): Product!
        countProducts: Long!
    }
    
    type Mutation {
        newProduct(name: String!, price: Int!): Product!
        deleteProduct(id: Long!): Boolean
        updateProductName(id: Long!, name: String!): Product!
        updateProductPrice(id: Long!, price: Int!): Product!
    }
    
    # user
    type User {
        id: Long!
        name: String!
        age: Int!
    }
    
    input CreateUserInput {
        name: String!
        age: Int!
    }
    
    
    extend type Query {
        users: [User]!
        user(id: Long): User!
        countUsers: Long!
    }
    
    extend type Mutation {
        newUser(name: String!, age: Int!): User!
        createUser(input: CreateUserInput!): User!
        deleteUser(id: Long!): Boolean
        updateUserName(id: Long!, name: String!): User!
        updateUserAge(id: Long!, age: Int!): User!
    }
    
    # purchase
    type Purchase {
        id: Long!
        date: String!
        product: Product!
        user: User!
    }
    
    extend type Query {
        purchases: [Purchase]!
        purchase(id: Long): Purchase!
    }
    
    extend type Mutation {
        newPurchase(productId: Long, userId: Long): Purchase!
    }

```

## Test
### Test Dataset (GraphqlApplication.java)
```
    User(id=1, name=이정환, age=30)
    User(id=2, name=김동주, age=25)
    User(id=3, name=송길주, age=20)
    User(id=4, name=양충현, age=15)
    
    Product(id=1, name=테스트1, price=1000)
    Product(id=2, name=테스트2, price=500)
    Product(id=3, name=테스트3, price=1000)
    Product(id=4, name=테스트4, price=500)
    Product(id=5, name=테스트5, price=1000)
    
    purchase(id=1, product_id=1, user_id=1, date)
    # -> purchase(id=1, product(id=1, ~~~), user(id=1, ~~~), date)    
```

### Test GraphQL Queries
- product
```
    {
        products {
            name
        }
    }
```

```
    {
        products {
            name
            price
        }
    }
```

```
    {
        product(id:3) {
            name
            price
        }
    }
```

- user
```
    {
        users {
            id, name, age
        }
    }
```
```
    {
        user(id:1) {
            id, name, age
        }
    }
```
```
    mutation {
        newUser(name:"윤호영", age:28) {
            name, age
        }
    }
```
```
    mutation createUser($input: CreateUserInput!) {
        createUser(input:$input) {id, name, age}
    }
    
    variables {
        "input" : { 
            "name" : "최재성",
            "age" : 19
         }
    }
```
```
    mutation { deleteUser(id:1) }
```
```
    mutation { 
        updateUserName(id:1, name:"박철") {
            id
            name
            age   
        }    
    }
```
```
    mutation { 
        updateUserAge(id:1, age:19) {
            id
            name
            age   
        }    
    }
```

- purchase
```
    {
        purchases { 
            product {
                name, price
            }
            user {
                name, age
            }
            date   
        }    
    }
```
```
    {
        purchase(id:1) { 
            product {
                name
            }
            user {
                name
            }
        }    
    }
```
```
    mutation {
        newPurchase(productId:3, userId:4) { 
            product {
                id, name, price
            }
            user {
                id, name, age
            }
            date
        }    
    }
```

TODO : exception 처리 

