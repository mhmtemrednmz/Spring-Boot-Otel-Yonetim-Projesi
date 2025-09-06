# 🏨 Otel Yönetim Sistemi

Spring Boot ile geliştirilmiş bir **otel yönetim ve rezervasyon uygulaması**.  
Uygulama, **JWT tabanlı güvenlik** ve **rol bazlı yetkilendirme** içerir.  
Otelleri, odaları, kullanıcıları, rezervasyonları ve ödemeleri kolayca yönetmenizi sağlar.

---

## ⚡ Özellikler

- Kullanıcı yönetimi (Admin, Manager, User)
- Güvenli giriş ve çıkış (JWT)
- Rol bazlı erişim kontrolü
- Otel, oda ve imkanlar için CRUD işlemleri
- Otel inceleme ve puanlama sistemi
- Favoriler ve beğeniler
- Oda rezervasyonu ve ödeme yönetimi
- Farklı ödeme yöntemleri
- Swagger API dokümantasyonu
- Rate limiting ile güvenlik (Bucket4j)

---

## 🛠 Teknolojiler

- Spring Boot 3.5
- Spring Data JPA
- PostgreSQL
- MapStruct
- Lombok
- JWT
- SpringDoc OpenAPI (Swagger)
- Bucket4j

---

## 🚀 Kurulum ve Çalıştırma

### Önkoşullar

- Java 17
- Maven
- PostgreSQL

### Adımlar

1. Repoyu klonlayın:

```bash
git clone https://github.com/KULLANICI_ADIN/Spring-Boot-Otel-Yonetim-Projesi.git
cd Spring-Boot-Otel-Yonetim-Projesi 
```

2. Veritabanı bilgilerinizi application.yml veya application.properties dosyasında güncelleyin:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/hotel_db
    username: postgres
    password: sifreniz
  jpa:
    hibernate:
  ddl-auto: update
show-sql: true
```
3. Uygulamayı çalıştırın:
```bash
mvn spring-boot:run
```

4. Swagger arayüzüne erişin:
```bash
http://localhost:8080/swagger-ui.html
```

---

## 🏗 Proje Yapısı
src/main/java/com/emrednmz/
- controller/ --> API controller'lar
- service/ --> İş mantığı
- repository/ --> Veritabanı işlemleri
- model/ --> Entity sınıfları
- dto/ --> Data Transfer Object'ler
- mapper/ --> MapStruct mapper'lar
- security/ --> JWT ve güvenlik yapılandırmaları
- exception/ --> Özel exceptionlar ve hata yönetimi

