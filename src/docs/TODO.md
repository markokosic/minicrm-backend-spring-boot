# 🏢 MiniCRM - Development Roadmap

> Last updated: November 2025

## 🎯 MILESTONE: v1.0 - MVP (Target: 15.03.2026)

### 🔐 Authentication 
- [ ] fix token validation on restart

### 👥 User Management
- [ ] Add users as super admin
- [ ] Update user profile
- [ ] User roles/permissions RBAC


### Customers CRUD (Business, Consumer)
- [X] Customers CRUD
- Search Customers by name, email, phone

### Addresses CRUD
- [ ] Address CRUD 
- [ ] Address types (billing, shipping, etc.)

### 🏢 Offers
- [ ] Orders CRUD
- [ ] Generate PDF offers + File Storage S3?


### 🏢 Orders
- [ ] Orders CRUD
- [ ] Generate PDF orders

### 🏢 Invoices
- [ ] Invoice CRUD
- [ ] Generate PDF invoice 

### AFTER IMPLEMENTING BASE FEATURES
- [ ] RabbitMQ for PDF generation
- [ ] Caching with Redis


### 📖 Documentation
- [x] Auth sequence diagram (Mermaid)
- [ ] Swagger/OpenAPI setup
- [ ] README with quick start guide


### 🔧 Infrastructure / DevOps
- [ ] Docker Compose (PostgreSQL + Backend)
- [ ] .env.example file
- [ ] GitHub Actions CI
- [ ] Environment variable config
- [ ] Deployment on VPS


---

## 💡 BACKLOG / IDEAS
### Security
- [ ] Email verification on registration
- [ ] Password reset flow
- [ ] Rate limiting for auth endpoints

### Features
- [ ] Audit log (who changed what)
- [ ] Dashboard analytics

### DevOps
- [ ] Environment-based configs (dev, staging, prod)
- [ ] Database backups

---





---