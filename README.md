Purrrfect Backend 🐾

Purrrfect Backend is a Spring Boot–based REST API that powers the Purrrfect pet platform.
It handles authentication, product management, chatbot integration, image uploads, and secure user operations.

Built with a scalable layered architecture following industry backend standards.

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

src/main/java/com/Purrrfect
│
├── Config
│   ├── SecurityConfig.java
│   ├── WebConfig.java
│   ├── CloudinaryConfig.java
│   └── AppConfig.java
│
├── Controller
│   ├── AuthController.java
│   ├── ProductController.java
│   ├── UserController.java
│   └── ChatbotController.java
│
├── Service
│   ├── ProductService.java
│   ├── ProductServiceImpl.java
│   ├── UserService.java
│   └── ChatbotService.java
│
├── Repo
│   ├── ProductRepo.java
│   ├── UserRepo.java
│   └── PetDetailsRepo.java
│
├── Model / DTO
│   ├── User.java
│   ├── Product.java
│   ├── PetDetails.java
│   ├── LoginRequest.java
│   └── SignupRequest.java
│
├── Security
│   ├── JwtAuthenticationFilter.java
│   ├── JwtTokenProvider.java
│   ├── JwtHelper.java
│   └── OAuth2SuccessHandler.java
│
└── PurrrfectBackendApplication.java


🔐 Authentication Features

JWT-based authentication

Role-based authorization

Google OAuth2 login

Secure password encryption

Custom authentication filters

🛍️ Product & Pet Features

Add / update / delete pets

Product listing APIs

Image upload via Cloudinary

Pet details management

MySQL persistence

🤖 AI Chatbot

Integrated Hugging Face inference API to:

Suggest pets

Answer user queries

Provide buying guidance

☁️ Image Upload

Cloudinary integration enables:

Secure image hosting

Fast CDN delivery

Product & pet image storage

⚙️ Setup & Installation
1️⃣ Clone repo
git clone https://github.com/ShreyanshChoudhary/purrrfect-backend.git
cd purrrfect-backend

2️⃣ Configure environment

Create:

application-secret.properties


Add credentials:

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

3️⃣ Run application

Using Maven wrapper:

./mvnw spring-boot:run


Or Windows:

mvnw.cmd spring-boot:run


App runs on:

http://localhost:8081

🔗 API Base URL
http://localhost:8081/api/


Example endpoints:

Method	Endpoint	Description
POST	/auth/login	User login
POST	/auth/signup	Register user
GET	/products	Get all pets/products
POST	/products	Add product
POST	/chatbot	AI chatbot query
🛡️ Security Notes

Secrets stored via external config

JWT signed tokens

OAuth2 secured login

CORS configured for frontend

📌 Future Enhancements

Payment gateway integration

Order management system

Admin analytics dashboard

Pet recommendation engine (ML)

👨‍💻 Author

Shreyansh Choudhary
Full Stack Developer (Spring Boot + React)

Backend APIs

AI chatbot integration

Secure authentication systems

