# study_graphql

# dependencies
- graphql-spring-boot 5.0.2
    - endpoint 및 config 자동 적용
- graphiql-spring-boot 3.6.0
    - graphql 요청 웹 UI 제공
- graphql-java-tools 5.2.0
    - resolver 제공

# db
- h2db (설치 : tpc로 사용할 경우.)
    - http://www.h2database.com/html/main.html
    
    
# Test Sample
### User
`
    id, name, age
`
### Product
`
    id, name, price
`
### Purchase
`
    id, date, product, user
`

# Test Case
- RestTemplate로 graphql endpoint 호출

# GraphQL Query Samples
