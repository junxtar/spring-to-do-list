# spring-to-do-list
## Jacoco

![스크린샷 2023-12-03 17 56 56](https://github.com/junxtar/spring-to-do-list/assets/75934088/861b9aca-b901-4f87-9d48-676e67a84098)

## ERD
<img width="877" alt="스크린샷 2023-11-13 15 16 54" src="https://github.com/junxtar/spring-to-do-list/assets/75934088/58a52c75-e1ff-400e-804d-e4bddc78d9d2">

## API 명세서 
[Postman API 명세서](https://documenter.getpostman.com/view/25748761/2s9Ye8fv3J#71b783cd-dca2-4b77-bd29-dbf2d877a5fe)


## 디렉토리 구조
```
.
├── java
│   └── com
│       └── sparta
│           └── springtodolist
│               ├── SpringToDoListApplication.java
│               ├── domain
│               │   ├── card
│               │   │   ├── constant
│               │   │   │   └── CardConstant.java
│               │   │   ├── controller
│               │   │   │   ├── CardController.java
│               │   │   │   └── dto
│               │   │   │       └── request
│               │   │   │           ├── CardCreateRequestDto.java
│               │   │   │           └── CardUpdateRequestDto.java
│               │   │   ├── entity
│               │   │   │   └── Card.java
│               │   │   ├── exception
│               │   │   │   ├── CardNotAccessException.java
│               │   │   │   └── CardNotFoundException.java
│               │   │   ├── repository
│               │   │   │   └── CardRepository.java
│               │   │   └── service
│               │   │       ├── CardService.java
│               │   │       └── dto
│               │   │           ├── request
│               │   │           │   ├── CardCreateServiceRequestDto.java
│               │   │           │   └── CardUpdateServiceRequestDto.java
│               │   │           └── response
│               │   │               ├── CardCompletedResponseDto.java
│               │   │               ├── CardCreateResponseDto.java
│               │   │               ├── CardPrivatedResponseDto.java
│               │   │               ├── CardResponseDto.java
│               │   │               └── SingleCardResponseDto.java
│               │   ├── comment 
│               │   │   ├── controller
│               │   │   │   ├── CommentController.java
│               │   │   │   └── dto
│               │   │   │       └── request
│               │   │   │           ├── CommentCreateRequestDto.java
│               │   │   │           └── CommentUpdateRequestDto.java
│               │   │   ├── entity
│               │   │   │   └── Comment.java
│               │   │   ├── exception
│               │   │   │   ├── CommentNotAccessException.java
│               │   │   │   └── CommentNotFoundException.java
│               │   │   ├── repository
│               │   │   │   └── CommentRepository.java
│               │   │   └── service
│               │   │       ├── CommentService.java
│               │   │       └── dto
│               │   │           ├── request
│               │   │           │   ├── CommentCreateServiceRequestDto.java
│               │   │           │   └── CommentUpdateServiceRequestDto.java
│               │   │           └── resopnse
│               │   │               └── CommentResponseDto.java
│               │   └── user
│               │       ├── controller
│               │       │   ├── UserController.java
│               │       │   └── dto
│               │       │       └── request
│               │       │           ├── UserLoginRequestDto.java
│               │       │           └── UserSignupRequestDto.java
│               │       ├── entity
│               │       │   └── User.java
│               │       ├── exception
│               │       │   └── ExistsUserException.java
│               │       ├── repository
│               │       │   └── UserRepository.java
│               │       └── service
│               │           ├── UserService.java
│               │           └── dto
│               │               ├── request
│               │               │   └── UserSignupServiceRequestDto.java
│               │               └── response
│               │                   └── UserSignupResponseDto.java
│               └── global
│                   ├── config
│                   │   ├── AuditingConfig.java
│                   │   ├── JasyptConfig.java
│                   │   ├── SwaggerConfig.java
│                   │   └── WebSecurityConfig.java
│                   ├── exception
│                   │   ├── CustomException.java
│                   │   ├── ErrorCode.java
│                   │   ├── advice
│                   │   │   └── GlobalExceptionHandler.java
│                   │   └── response
│                   │       └── ErrorResponse.java
│                   ├── secutiry
│                   │   ├── JwtAuthenticationFilter.java
│                   │   ├── JwtAuthorizationFilter.java
│                   │   ├── JwtTokenProvider.java
│                   │   ├── TokenInfo.java
│                   │   ├── UserDetailsImpl.java
│                   │   └── UserDetailsServiceImpl.java
│                   └── util
│                       └── BaseEntity.java
└── resources
    └── application.yml
```

