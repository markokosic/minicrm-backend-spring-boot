# 🏢 MiniCRM - Development Roadmap

> Last updated: November 2025

## 🎯 MILESTONE: v1.0 - MVP (Target: 01.12.2025)

### 🔐 Authentication [90%]
- [x] User registration with tenant creation
- [x] Login with JWT (access + refresh tokens)
- [x] JWT Filter for request authentication
- [x] Tenant context isolation (ThreadLocal)
- [x] Refresh token endpoint
- [x] Get current user (/me endpoint)
- [x] Logout endpoint (clear cookies)
- [ ] Unit tests (80% coverage target)
- [ ] Integration tests
- [ ] Password validation rules
- [ ] Swagger documentation

### 👥 User Management [0%]
- [x] Get current user
- [x] Create user (via registration)
- [ ] List all users (admin, with tenant filter)
- [ ] Update user profile
- [ ] Soft delete user
- [ ] User roles/permissions

### 🏢 Companies CRUD [0%]
- [ ] GET /api/companies (with tenant filter)
- [ ] GET /api/companies/{id}
- [ ] POST /api/companies
- [ ] PUT /api/companies/{id}
- [ ] DELETE /api/companies/{id} (soft delete)
- [ ] Validation (VAT format, email, etc.)
- [ ] Unit tests (80% coverage target)

### Customers CRUD [0%]
- [ ] Customers basic CRUD
- [ ] Link customer to company OR person
- [ ] Customer types handling

### Persons CRUD [0%]
- [ ] Persons basic CRUD
- [ ] Link person to company
- [ ] Position/role in company

### Addresses CRUD [0%]
- [ ] Address CRUD for customers
- [ ] Address types (billing, shipping, etc.)
- [ ] Validation (country, zip, etc.)

### 🏢 Offers [0%]

### 🏢 Orders [0%]

### 🏢 Invoices [0%]


### 📖 Documentation [10%]
- [x] Auth sequence diagram (Mermaid)
- [ ] Swagger/OpenAPI setup
- [ ] README with quick start guide
- [ ] API usage examples
- [ ] Architecture overview

### 🧪 Testing [0%️]
- [x] Basic AuthServiceTest
- [ ] Complete AuthService tests (all methods)
- [ ] JWTService tests
- [ ] Controller integration tests
- [ ] **Target: 50% coverage**

### 🔧 Infrastructure / DevOps [0%]
- [ ] Docker Compose (PostgreSQL + Backend)
- [ ] .env.example file
- [ ] GitHub Actions CI
- [ ] Environment variable config
- [ ] Deployment on ???? AWS/Google/VPS?

## 🐛 KNOWN ISSUES

### Critical 🔴
- JWT secret gets overwritten in constructor → Token validation fails on restart

### Medium 🟡
- No validation on some endpoints (firstName, lastName can be empty)
- Missing error handling for expired refresh tokens
- Application.properties has hardcoded credentials

### Low 🟢
- Commented-out code in several files
- @GetMapping annotation in Service class (AuthService line 131)
- Generic RuntimeException used instead of custom exceptions

---

## 💡 BACKLOG / IDEAS

### Security
- [ ] Email verification on registration
- [ ] Password reset flow
- [ ] Rate limiting for auth endpoints
- [ ] Refresh token rotation
- [ ] Store refresh tokens in database

### Features
- [ ] Advanced search/filters
- [ ] Export data to CSV
- [ ] Import companies from CSV
- [ ] Audit log (who changed what)
- [ ] Dashboard analytics

### DevOps
- [ ] Environment-based configs (dev, staging, prod)
- [ ] Database backups
- [ ] Monitoring (Sentry, Grafana)

### Code Quality
- [ ] SonarCloud integration
- [ ] Codecov for test coverage
- [ ] Pre-commit hooks (format, lint)
- [ ] API versioning (/api/v1/...)
---





---