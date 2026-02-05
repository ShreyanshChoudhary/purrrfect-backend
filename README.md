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

📂 Project Structure
src/main/java/com/Purrrfect
│
├── Config
│   ├── SecurityConfig.java        # Spring Security & JWT config
│   ├── WebConfig.java             # CORS & Web settings
│   ├── CloudinaryConfig.java      # Cloudinary integration
│   └── AppConfig.java             # General bean configs
│
├── Controller                    # REST API endpoints
│   ├── AuthController.java
│   ├── ProductController.java
│   ├── UserController.java
│   └── ChatbotController.java
│
├── Service                      # Business logic layer
│   ├── ProductService.java
│   ├── ProductServiceImpl.java
│   ├── UserService.java
│   └── ChatbotService.java
│
├── Repo                         # Database repositories
│   ├── ProductRepo.java
│   ├── UserRepo.java
│   └── PetDetailsRepo.java
│
├── Model / DTO                  # Entities & request payloads
│   ├── User.java
│   ├── Product.java
│   ├── PetDetails.java
│   ├── LoginRequest.java
│   └── SignupRequest.java
│
├── Security                     # Auth & token handling
│   ├── JwtAuthenticationFilter.java
│   ├── JwtTokenProvider.java
│   ├── JwtHelper.java
│   └── OAuth2SuccessHandler.java
│
└── PurrrfectBackendApplication.java   # Main Spring Boot entry point

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

👨‍💻 Author

Shreyansh Choudhary
Full Stack Developer — Spring Boot & React
