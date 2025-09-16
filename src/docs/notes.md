# CRM API Routing (B2B, B2C, Employees)

## 1. Auth / Tenant
| Methode | Route | Beschreibung |
|---------|-------|--------------|
| POST    | `/api/auth/register` | Tenant + Admin-User registrieren |
| POST    | `/api/auth/login` | Login, JWT + Refresh Token |
| POST    | `/api/auth/refresh` | Refresh Token erneuern |
| POST    | `/api/auth/logout` | Refresh Token invalidieren |

---

## 2. Users (Systemnutzer)
| Methode | Route | Beschreibung |
|---------|-------|--------------|
| GET     | `/api/users` | Alle User im Tenant |
| GET     | `/api/users/{id}` | Einzelner User |
| POST    | `/api/users` | Neuen User erstellen |
| PUT     | `/api/users/{id}` | User aktualisieren |
| DELETE  | `/api/users/{id}` | User deaktivieren / archivieren |

---

## 3. Companies (B2B-Kunden)
| Methode | Route | Beschreibung |
|---------|-------|--------------|
| GET     | `/api/companies` | Alle Firmen im Tenant |
| GET     | `/api/companies/{id}` | Einzelne Firma |
| POST    | `/api/companies` | Neue Firma erstellen |
| PUT     | `/api/companies/{id}` | Firma aktualisieren |
| DELETE  | `/api/companies/{id}` | Firma archivieren / löschen |

### Employees (Mitarbeiter / Ansprechpartner der Firma)
| Methode | Route | Beschreibung |
|---------|-------|--------------|
| GET     | `/api/companies/{companyId}/employees` | Alle Mitarbeiter der Firma |
| GET     | `/api/employees/{id}` | Einzelner Mitarbeiter |
| POST    | `/api/companies/{companyId}/employees` | Mitarbeiter erstellen / Firma zuordnen |
| PUT     | `/api/employees/{id}` | Mitarbeiter aktualisieren |
| DELETE  | `/api/employees/{id}` | Mitarbeiter deaktivieren / entfernen |

---

## 4. Persons (B2C-Kunden)
| Methode | Route | Beschreibung |
|---------|-------|--------------|
| GET     | `/api/persons` | Alle Personen im Tenant |
| GET     | `/api/persons/{id}` | Einzelne Person |
| POST    | `/api/persons` | Neue Person erstellen |
| PUT     | `/api/persons/{id}` | Person aktualisieren |
| DELETE  | `/api/persons/{id}` | Person archivieren / löschen |

---

## 5. Addresses
| Methode | Route | Beschreibung |
|---------|-------|--------------|
| GET     | `/api/companies/{companyId}/addresses` | Adressen einer Firma |
| GET     | `/api/employees/{employeeId}/addresses` | Adressen eines Mitarbeiters |
| GET     | `/api/persons/{personId}/addresses` | Adressen einer Person |
| POST    | `/api/companies/{companyId}/addresses` | Neue Adresse hinzufügen |
| POST    | `/api/employees/{employeeId}/addresses` | Neue Adresse hinzufügen |
| POST    | `/api/persons/{personId}/addresses` | Neue Adresse hinzufügen |
| PUT     | `/api/addresses/{id}` | Adresse aktualisieren |
| DELETE  | `/api/addresses/{id}` | Adresse löschen |

### Filter / Query Params
- `/api/companies?search=name` → Firmen suchen
- `/api/employees?search=firstName,lastName,email` → Mitarbeiter suchen
- `/api/persons?search=firstName,lastName,email` → B2C-Kunden suchen
- `/api/users?status=active` → aktive User
