# SpringSecurity-JWT 란 무엇인가?

</br>

👉👉👉 [[Spring Security] JWT(JSON Web Token) 의 모든 것 - 찰나의 개발흔적](https://aonee.tistory.com/70, "[Spring Security] JWT(JSON Web Token) 의 모든 것 - 찰나의 개발흔적")

</br>

👉👉👉 [[Spring Security] JWT(JSON Web Token) 의 코드 구현 - 찰나의 개발흔적](https://aonee.tistory.com/70, "[Spring Security] JWT(JSON Web Token) 의 코드 구현 - 찰나의 개발흔적")

</br>

## 1. 정의

**#JSON 	#Claim_based_Token 	#Self_contained**

JSON Web Token의 약자로 클레임 토큰 기반의 방식. 

클라이언트의 세션 상태를 저장하는 것이 아닌 
필요한 정보를 토큰 body에 저장해 클라이언트에 저장해두고 이를 증명서 처럼 사용한다.

> Claim based?

- Claim : 사용자에 대한 프로퍼티 / 속성
- 토큰 자체가 정보
- Self-contained : 자체 포함, 즉 토큰 자체가 정보
- key / value 로 이루어짐



## 2. 구성

{Header}.{Payload}.{Verify Signature}
3가지 정보를 '.'로 연결하여 사용한다.

> [JWT 공식사이트](https://jwt.io/) 를 통해 JWT를 생성 및 검증할 수 있다.



![image](https://user-images.githubusercontent.com/49062985/81495668-f2b35e80-92ec-11ea-82ec-d886f275c0da.png)



**Header** : JWT 토큰의 유형이나 사용된 해시 알고리즘의 정보가 들어간다.

**Payload** : 클레임(Claims)을 포함한다. 즉, 클라이언트에 대한 정보가 담겨있다. 
				또한 여기에는 iss, sub, aud, exp, nbf, iat, jti 와 같은 기본 정보가 들어간다.

> **1. 등록된 클레임** ( [**Registered claims**](https://tools.ietf.org/html/rfc7519#section-4.1) **)**
> 이미 정의된 클레임들로 무조건 따라야 하는 것은 아니지만 권장한다. 
> **iss** (issuer, 토큰 발행자), **exp** (expiration time, 토큰 만료시간), **sub** (subject, 토큰 제목), **aud**(audience, 토큰 대상자) 와 같은 클레임들이 있습니다. 
> 클레임의 이름은 compact를 위해 3글자로 사용한다.
>
> **2. 공개된 클레임** ( [**Public claims**](https://tools.ietf.org/html/rfc7519#section-4.2) )
> JWT를 사용하는 사람들에 의해 정의되는 클레임이다. 
> 클레임 충돌을 피하기 위해서 [IANA JSON Web Token Registry](https://www.iana.org/assignments/jwt/jwt.xhtml) 에 정의하거나 URI 형식으로 정의해야 한다.
>
> **3. 비공개 클레임** ( [**Private claims**](https://tools.ietf.org/html/rfc7519#section-4.3) )
> GUI, 서버, 그 외 모듈간에 협의한 클레임이다.

**Signature** : header에서 지정한 알고리즘과 secret key로 Header와 Payload를 담는다.

> 서명을 통해 메시지가 중간에 변경되지 않았다는 것을 검증하며, 
> JWT를 보낸 사람이 신뢰할 수 있는 대상이라는 것도 알 수 있다.



## 3. 특징

- **1. 표준 RFC 7519**
  JSON Web Token은 정보를 안전하게 전송하기 위해 정의된 공개된 표준 (RFC 7519) 이다

- **2. Self-contained ( 자가 수용적 )**
  JWT 자체적으로 필요한 모든 정보를 포함한다.
  헤더 정보와, 실제 전달할 데이터, 검증할 수 있는 서명 데이터를 모두 포함하고 있다.

- **3. 신뢰 가능**
  JWT는 디지털 서명에 의해 검증할 수 있으며 신뢰할 수 있다.
  비밀 값을 사용하는 HMAC 알고리즘이나 RDS or ECDSA와 같은 공개키, 개인키 쌍으로 서명될 수 있다.

- **4. Payload는 공개 데이터**

  JWT에 정보는 누구나 https://jwt.io/ 페이지에 접속해서 정보를 확인할 수 있다.
  따라서 비밀번호와 같은 보안이 필요한 정보는 payload에 저장하면 안된다.

- **5. JWT의 Secret Key**

  JWT에서는 정보는 공개가 되어있지만 해시 값을 통해서 정보가 유효한지 확인을 하게 됩니다. 
  따라서 시크릿 키가 유출이 된다면 JWT에서 보안상에 큰 위협이 된다.



## 4. 과정

- **4.1 로그인**

  ![image](https://user-images.githubusercontent.com/49062985/81495542-e8449500-92eb-11ea-9cc5-316568379779.png)

  - client는 Auth Server에 로그인을 합니다. 이때 Auth Server는 application server 내에 위치할 수도 있으며, facebook, goole과 같은 제 3자가 될 수도 있다.

    • Auth Server에서 인증을 완료한 사용자는 JWT Token을 전달받는다.

    • client는 application server에 resource를 요청할때 앞서 전달받은 JWT Token을 Authorization Header에 전달한다.

    • application server는 전달받은 JWT Token이 유효하면 200 ok와 함께 data를 response 한다.

    > 이때 중요한점은 application server가 더이상 로그인한 사용자의 session을 관리하지 않는다는 것 입니다. 단지 전달받은 JWT가 유효한 Token인지만 확인하면 된다. 

    또한, outside domain인 client가 application server에 resource를 요청하기위해 application server는 cors를 enable 해야한다.

    cors를 enable 하지 않으면 기본적으로 application server는 외부 domain에 resource 접근을 허용하지 않는다.

    

- **4.2 사용 환경 **

  ![image](https://user-images.githubusercontent.com/49062985/81495622-8c2e4080-92ec-11ea-971a-f9e1ec2689bf.png)

  * 하나의 End Point가 아닌 Mobile/Web 등의 multiple endpoint 환경이라면 통합적인 인증/인가 환경을 제공하기위해선 반드시 JWT를 사용한다.

  * 보편적인 예로, React로 client를 구성하고 REST API 서버를 따로 두는 경우에는 JWT를 사용한다.

  * 3rd party에게 public 하게 open 한 REST Endpoint가 존재할 경우 해당 3rd party의 인증/인가를 관리하기 위해 JWT를 사용한다.

  


## 5. 장점 / 단점 & 도입시 고려사항

### 장점

1. 다양한 클라이언트(web,ios,android) 커버 가능

2. 보안 이슈
   사용자가 자신의 비밀 값으로 서버에 로그인 하게 되면, 서버는 JWT를 리턴합니다. **token을 인증 값으로 사용하게 되면 기존 쿠키/세션을 사용하는 방식보다 많은 보안 이슈를 막을 수 있습니다**. 서버는 GUI로부터 받은 JWT가 유효할 경우, resouce를 사용하도록 허용합니다. 또한 JWT는 쿠키를 사용하지 않기 때문에, Cross-Origin Resource Sharing (CORS) 이슈가 발생하지 않습니다.

3. 데이터 용량: JWT는 기존의 XML보다 덜 복잡하고 인코딩 된 사이즈가 작습니다. 따라서 HTTP와 HTML 환경에서 사용하기 좋습니다.

4. 사용성: JSON parser는 대부분의 프로그래밍이 지원하기 때문에 XML을 사용하는 SAML 보다 만들기 쉽습니다.

5. CORS(Cross-origin resource sharing) 문제때문에 주로 쓴다.

6. session의 한계 극복 가능

   > **세션의 한계**
   >
   > 1. 서버 확장시 세션 정보의 동기화 문제
   >
   >    > 서버가 스케일아웃 돼서 여러 개가 생기면 각 서버마다 세션 정보가 저장로그인시(서버1), 새로고침(서버2) 로 접근하면 서버는 인증이 안됐다고 판단
   >
   > > 
   >
   > 2. 서버 / 세션 저장소의 부하
   > 3. 웹 / 앱 간의 상이한 쿠키-세션 처리 로직



### 단점 & 도입시 고려사항

1. **Self-contained**
   토큰 자체에 정보가 있다는 사실은 양날의 검

2. **토큰 길이**
   토큰 자체 payload 에 Claim set을 저장하기 때문에 정보가 많아질 수록 토큰의 길이가 늘어나 네트워크에 부하를 줄 수 있음

3. **payload 암호화**
   payload 자체는 그냥 인코딩된 데이터
   중간에 payload를 탈취하면 디코딩을 통해 테이터를 볼 수 있음
   payload에 중요 데이터를 넣지 않아야 함

4. **Stateless**
   토큰은 한번 만들면 서버에서 제어가 불가능



🐥 참고

https://www.youtube.com/watch?v=x6bzYyMY0GA&list=PLVApX3evDwJ1d0lKKHssPQvzv2Ao3e__Q&index=4

[https://medium.com/sjk5766/jwt-json-web-token-%EC%86%8C%EA%B0%9C-49e211c65b45](https://medium.com/sjk5766/jwt-json-web-token-소개-49e211c65b45)

https://sanghaklee.tistory.com/47



#

</br>

</br>

# SpringSecurity-JWT 구현
### jwtUtil

```
extractUsername
extractExpiration
extractClaim
extractAllClaims
isTokenExpired
createToken
validateToken
```
![image](https://user-images.githubusercontent.com/49062985/81705362-ede7d980-94a9-11ea-86fa-f04cce88dfb9.png)





![image](https://user-images.githubusercontent.com/49062985/81712284-63a27400-94af-11ea-900c-0820e7c899dc.png)

![image](https://user-images.githubusercontent.com/49062985/81712493-9ea4a780-94af-11ea-8f06-4c3ec96bd208.png)



![image](https://user-images.githubusercontent.com/49062985/81712743-ef1c0500-94af-11ea-8040-e8e26937e53d.png)

![image](https://user-images.githubusercontent.com/49062985/81717642-c3038280-94b5-11ea-8755-ac46ffca991e.png)




# 1. 동작과정

### Generating JWT

![generate jwt](https://user-images.githubusercontent.com/49062985/82150947-de470700-9894-11ea-8ae1-473762d61e80.jpg)

1. Client : 로그인 요청 POST (id, pw)
2. Server : id, pw가 맞는지 확인 후 맞다면 JWT를 SecretKey로 생성
3. Client : Server에게 받은 JWT를 로컬 or 세션에 저장
4. Client : 서버에 요청할 때 항상 헤더에 Token을 포함시킴
5. Server : 요청을 받을 때마다 SecretKey를 이용해 Token이 유효한지 검증
   * 서버만이 SecretKey를 가지고 있기 때문에 검증 가능
   * Token이 검증되면 따로 username, pw를 검사하지 않아도 사용자 인증 가능
6. Server : response

![AuthenticationManager](https://user-images.githubusercontent.com/49062985/82150976-0b93b500-9895-11ea-9ee8-ae2ea3196e8b.jpg)





# 2. Token 유효 검증

1. 클라이언트의 요청 (Header : Token)
2. Spring의 Interceptor에 의해 요청이 Intercept됨
3. 클라이언트에게 제공되었던 Token과 클라이언트의 Header에 담긴 Token 일치 확인
4. `auth0 JWT`를 이용해 `issuer, expire` 검증

### Validating JWT

![validating jwt](https://user-images.githubusercontent.com/49062985/82150987-12bac300-9895-11ea-8430-a89e4d20a72d.jpg)







# 3. Spring Boot Security + JWT 코드

## 3-1. dependency 추가

### pom.xml

```xml
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
<groupId>io.jsonwebtoken</groupId>
<artifactId>jjwt</artifactId>
<version>0.9.1</version>
</dependency>
```

- `spring-boot-starter-security`와 `jjwt` 추가

<br>

<br>

## 3-2. Secret Key 설정

- Hashing algorithm과 함께 사용할 Secret Key를 설정
- Secret Key는 Header, Payload와 결합되어 Hash 생성

### application.properties

```
jwt.secret=aoneeJjangjwt
```

<br>

<br>

## 3. JwtUtil

- JWT를 생성하고 검증하는 역할 수행
- `io.jsonwebtoken.Jwts` 라이브러리 사용

```
@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
```

### doGenerateToken

- Token 생성
  - **claim** : Token에 담을 정보
  - **issuer** : Token 발급자
  - **subject** : Token 제목
  - **issuedate** : Token 발급 시간
  - **expiration** : Token 만료 시간
    - milliseconds 기준!
    - `JWT_TOKEN_VALIDITY` = 5 * 60 * 60  => **5시간**
- `signWith` (알고리즘, 비밀키)

<br>

<br>

## 4. MyUserDetailsService

- DB에서 UserDetail를 얻어와 AuthenticationManager에게 제공하는 역할
- 이번에는 DB 없이 하드코딩된 User List에서 get userDetail

```
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new User("aonee","$2a$10$VKu6eW.2pHLJn3yeW0eMxuEUBxXCq/b2Vo3HwSqROGI2mmYRnXqpm",new ArrayList<>());
    }
}
```

- Spring Security 5.0에서는 Password를 BryptEncoder를 통해 Brypt화한다.
  - https://www.javainuse.com/onlineBcrypt 에서 user_pw를 Bcrypt화
    - $2a$10$VKu6eW.2pHLJn3yeW0eMxuEUBxXCq/b2Vo3HwSqROGI2mmYRnXqpm
- id : user_id, pw: user_pw로 고정해 사용자 확인
- 사용자 확인 실패시 throw Exception

<br>

<br>

## 5. LoginController

- 사용자가 입력한 id, pw를 body에 넣어서 **POST** API mapping **/authenticate**
- 사용자의 id, pw를 검증
- **jwtUtil**을 호출해 Token을 생성하고  **JwtResponse**에 Token을 담아  return ResponseEntity

```
@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;


    @GetMapping("/main")
    public String main(){
        return "Welcome!! This is main page";
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword())
            );

        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}

```

<br>

<br>

## 6. AuthenticationRequest

- 사용자에게서 받은 id, pw를 저장

```
@Getter
@Setter
public class AuthenticationRequest {
    private String username;
    private String password;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

```

<br>

<br>

## 7. AuthenticationResponse

* 사용자에게 반환될 JWT를 담은 Response

```
@Getter
public class AuthenticationResponse {
    private final String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }
}

```

<br>

<br>

## 8. JwtRequestFilter

- Client의 Request를 Intercept해서 Header의  Token가 유효한지 검증
- if 유효한 Token
  - Spring Security의 Authentication을 Setting, to specify that the current user is authenticated

```
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String username= null;
        String jwt = null;

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if(jwtUtil.validateToken(jwt, userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request,response);
    }
}

```



