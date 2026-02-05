🐾 Purrrfect Backend

Backend REST API for the Purrrfect Pet Platform built with Spring Boot.
Handles authentication, pet/product management, chatbot AI integration, and secure media uploads.

🚀 Tech Stack

Java 17

Spring Boot

Spring Security + JWT

OAuth2 (Google Login)

Spring Data JPA / Hibernate

MySQL

Cloudinary (Image Uploads)

Hugging Face API (AI Chatbot)

Maven


  <li><b>📁 Config</b>
    <ul>
      <li><code>SecurityConfig.java</code> — Spring Security & JWT config</li>
      <li><code>WebConfig.java</code> — CORS & Web settings</li>
      <li><code>CloudinaryConfig.java</code> — Cloudinary integration</li>
      <li><code>AppConfig.java</code> — Bean configs</li>
    </ul>
  </li>

  <li><b>📁 Controller</b>
    <ul>
      <li><code>AuthController.java</code></li>
      <li><code>ProductController.java</code></li>
      <li><code>UserController.java</code></li>
      <li><code>ChatbotController.java</code></li>
    </ul>
  </li>

  <li><b>📁 Service</b>
    <ul>
      <li><code>ProductService.java</code></li>
      <li><code>ProductServiceImpl.java</code></li>
      <li><code>UserService.java</code></li>
      <li><code>ChatbotService.java</code></li>
    </ul>
  </li>

  <li><b>📁 Repo</b>
    <ul>
      <li><code>ProductRepo.java</code></li>
      <li><code>UserRepo.java</code></li>
      <li><code>PetDetailsRepo.java</code></li>
    </ul>
  </li>

  <li><b>📁 Model / DTO</b>
    <ul>
      <li><code>User.java</code></li>
      <li><code>Product.java</code></li>
      <li><code>PetDetails.java</code></li>
      <li><code>LoginRequest.java</code></li>
      <li><code>SignupRequest.java</code></li>
    </ul>
  </li>

  <li><b>📁 Security</b>
    <ul>
      <li><code>JwtAuthenticationFilter.java</code></li>
      <li><code>JwtTokenProvider.java</code></li>
      <li><code>JwtHelper.java</code></li>
      <li><code>OAuth2SuccessHandler.java</code></li>
    </ul>
  </li>

  <li>
    <code>PurrrfectBackendApplication.java</code> — Main entry point
  </li>

</ul>



🔐 Authentication Features

JWT-based authentication

Role-based authorization

Google OAuth2 login

Secure password encryption

Custom authentication filters


🛍️ Product & Pet Features

Add / update / delete pets

Product listing APIs

Pet details management

MySQL database persistence

Image uploads via Cloudinary


🤖 AI Chatbot Integration

Powered by Hugging Face Inference API:

Pet recommendations

Buyer guidance

FAQ automation


☁️ Media Storage

Cloudinary Integration

Secure image hosting

CDN delivery

Optimized pet/product images

⚙️ Local Setup
1️⃣ Clone repository
git clone https://github.com/ShreyanshChoudhary/purrrfect-backend.git
cd purrrfect-backend

2️⃣ Configure secrets

Create file:

src/main/resources/application-secret.properties


Example:

DB_USERNAME=your_db_user
DB_PASSWORD=your_db_pass
JWT_SECRET=your_secret
GOOGLE_CLIENT_ID=your_id
GOOGLE_CLIENT_SECRET=your_secret
CLOUDINARY_NAME=xxx
CLOUDINARY_KEY=xxx
CLOUDINARY_SECRET=xxx
HF_TOKEN=xxx
MAIL_USER=xxx
MAIL_PASS=xxx

3️⃣ Run backend

Linux / Mac:

./mvnw spring-boot:run


Windows:

mvnw.cmd spring-boot:run


App runs at:

http://localhost:8081

🔗 API Base URL
http://localhost:8081/api/

Sample Endpoints
Method	Endpoint	Description
POST	/auth/signup	Register user
POST	/auth/login	Login
GET	/products	Get pets/products
POST	/products	Add product
POST	/chatbot	AI query
🛡️ Security Practices

Externalized secrets

JWT signed tokens

OAuth2 authentication

CORS configured

Encrypted credentials

📌 Future Enhancements

Payment gateway integration

Order management

Admin dashboard

ML pet recommendation engine



---


## 🔄 API Flow

```mermaid
sequenceDiagram

participant U as User
participant F as Frontend
participant B as Backend
participant DB as Database
participant AI as HuggingFace

U->>F: Request action
F->>B: API call
B->>DB: Fetch / Store data
B->>AI: Chatbot query
AI-->>B: Response
B-->>F: JSON response
F-->>U: UI update


🏗️ System Architecture

```mermaid
flowchart LR

A[React Frontend] -->|REST API| B[Spring Boot Backend]

B --> C[JWT Authentication]
B --> D[Google OAuth2]

B --> E[Chatbot Service]
E -->|API Call| F[Hugging Face AI]

B --> G[Cloudinary]
B --> H[MySQL Database]

C --> H
D --> H


