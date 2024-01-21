**Course Registration Form**

Bu Java Swing uygulaması, kurs bilgilerini girmek, kaydetmek ve görüntülemek için bir arayüz sağlar.

**Sınıflar**

**1. Course**

Bu sınıf, bir kursun özelliklerini (kurs kodu, kurs adı, dönem, öğretim görevlisi) temsil eder.

- **courseCode:** Kursun benzersiz kodu.
- **courseName:** Kursun adı.
- **courseSemester:** Kursun dönemi.
- **lecturer:** Kursun öğretim görevlisi.

**2. CourseForm**

Bu sınıf, Java Swing JFrame kullanarak bir kurs kayıt formu oluşturur.

Özellikler

- **courseCodeField:** Kurs kodu giriş alanı.
- **courseNameField:** Kurs adı giriş alanı.
- **courseSemesterField:** Kurs dönemi giriş alanı.
- **lecturerComboBox:** Öğretim görevlisi seçim kutusu.
- **saveButton:** Kursu kaydetmek için düğme.
- **courseTable:** Kaydedilmiş kurs bilgilerini görüntülemek için tablo.
- **filterTextField:** Kursları filtrelemek için metin alanı.
- **tableModel:** Kurs tablosu veri modeli.

Metodlar

- **createStyledButton(String buttonText):** Stilize düğme oluşturur.
- **validationCheck():** Kullanıcı girişlerini doğrular.
- **saveCourse():** Girilen kurs bilgilerini JSON dosyasına kaydeder.
- **readLecturers(String fileName):** Öğretim görevlilerini JSON dosyasından okur.
- **writeCourse(JSONObject courseJson):** Kurs bilgilerini JSON dosyasına yazar.
- **refreshCourseTable():** Kurs tablosunu günceller.
- **readCourses(String fileName):** Kaydedilmiş kurs bilgilerini JSON dosyasından okur.
- **filterCourseTable():** Kurs tablosunu metin filtresine göre filtreler.
- **createLabel(String text):** Etiket oluşturur.

Ana Metod

- **main(String[] args):** Uygulamayı başlatan ana metod.

**Nasıl Kullanılır?**

1. Uygulama başlatıldığında, bir kurs eklemek için gerekli bilgileri girebilirsiniz.
2. "Save" düğmesine tıkladığınızda, girilen kurs bilgileri kaydedilir.
3. Kaydedilen kurslar tablo üzerinde görüntülenir.
4. "Search" alanına yazılan metinle kursları filtreleyebilirsiniz.

**Bağımlılıklar**

- JSON Simple Library: JSON işlemleri için kullanılmıştır. (JSON dosyalarını okuma ve yazma)

**Lecturer Registration Form**

Bu Java Swing uygulaması, öğretim görevlileri hakkında bilgi girmek, kaydetmek ve görüntülemek için bir arayüz sağlar.

**Sınıflar**

**1. LecturerForm**

Bu sınıf, Java Swing kullanarak bir öğretim görevlisi kayıt formu oluşturur.

Özellikler

- **teacherNoField:** Öğretim görevlisinin benzersiz numarasını girmek için alan.
- **nameField:** Öğretim görevlisinin adını girmek için alan.
- **surnameField:** Öğretim görevlisinin soyadını girmek için alan.
- **departmentField:** Öğretim görevlisinin bölümünü girmek için alan.
- **saveButton:** Öğretim görevlisi bilgilerini kaydetmek için düğme.
- **lecturerTable:** Kaydedilmiş öğretim görevlisi bilgilerini görüntülemek için tablo.
- **filterTextField:** Öğretim görevlilerini filtrelemek için metin alanı.
- **tableModel:** Öğretim görevlisi tablosu veri modeli.

Metodlar

- **saveLecturer():** Girilen öğretim görevlisi bilgilerini bir JSON dosyasına kaydeder.
- **refreshLecturerTable():** Öğretim görevlisi tablosunu en güncel verilerle yeniler.
- **createLabel(String text):** Biçimli bir etiket oluşturur.
- **createStyledButton(String buttonText):** Biçimli bir düğme oluşturur.
- **readLecturers(String fileName):** Öğretim görevlisi bilgilerini bir JSON dosyasından okur.
- **filterLecturerTable():** Kullanıcı girişi temelinde öğretim görevlisi tablosunu filtreler.

Ana Metod

- **main(String[] args):** Uygulamayı başlatır.

**Nasıl Kullanılır?**

1. Uygulama başlatıldığında, yeni bir öğretim görevlisi eklemek için gerekli bilgileri girin.
2. Öğretim görevlisi bilgilerini kaydetmek için "Kaydet" düğmesine tıklayın.
3. Tabloda kaydedilen öğretim görevlisi bilgilerini görüntüleyin.
4. Öğretim görevlilerini filtrelemek için "Ara" alanını kullanın.

**Bağımlılıklar**

- JSON Simple Kütüphanesi: JSON işlemleri için kullanılmıştır. (JSON dosyalarını okuma ve yazma)

**Not**

- Öğretim görevlisi bilgileri "lecturers.json" adlı bir JSON dosyasında saklanır.
- Uygulama, temel doğrulama ve filtreleme işlevselliği sağlar.

**Student Registration**  **Form**

Bu Java Swing uygulaması, öğrenci bilgilerini girmek, kaydetmek ve görüntülemek için bir arayüz sağlar.

**Sınıflar**

**1. StudentForm**

Bu sınıf, Java Swing kullanarak bir öğrenci kayıt formu oluşturur.

Özellikler

- **studentNumberField:** Öğrenci numarasını girmek için alan.
- **studentNameField:** Öğrenci adını girmek için alan.
- **studentSurnameField:** Öğrenci soyadını girmek için alan.
- **studentDepartmentField:** Öğrenci bölümünü girmek için alan.
- **studentCoursesField:** Öğrencinin aldığı dersleri seçmek için alan.
- **saveButton:** Öğrenci bilgilerini kaydetmek için düğme.
- **studentTable:** Kaydedilmiş öğrenci bilgilerini görüntülemek için tablo.
- **filterTextField:** Öğrencileri filtrelemek için metin alanı.
- **tableModel:** Öğrenci tablosu veri modeli.

Metodlar

- **readCourses():** Dersleri okuyarak bir liste döndürür.
- **createStyledButton(String buttonText):** Biçimli bir düğme oluşturur.
- **createStyledComboBox():** Biçimli bir açılır liste oluşturur.
- **validationCheck():** Giriş doğrulaması yapar.
- **saveStudent():** Girilen öğrenci bilgilerini bir JSON dosyasına kaydeder.
- **refreshStudentTable():** Öğrenci tablosunu en güncel verilerle yeniler.
- **readStudents(String fileName):** Öğrenci bilgilerini bir JSON dosyasından okur.
- **printStudent(JSONObject studentJson):** Öğrenci bilgilerini ekrana yazdırır.
- **filterStudentTable():** Kullanıcı girişi temelinde öğrenci tablosunu filtreler.
- **createLabel(String text):** Biçimli bir etiket oluşturur.

Ana Metod

- **main(String[] args):** Uygulamayı başlatır.

**Nasıl Kullanılır?**

1. Uygulama başlatıldığında, yeni bir öğrenci eklemek için gerekli bilgileri girin.
2. "Kaydet" düğmesine tıklayarak öğrenci bilgilerini kaydedin.
3. Kaydedilen öğrenci bilgilerini tabloda görüntüleyin.
4. "Ara" alanını kullanarak öğrencileri filtreleyin.

**Bağımlılıklar**

- JSON Simple Kütüphanesi: JSON işlemleri için kullanılmıştır. (JSON dosyalarını okuma ve yazma)

**Not**

- Öğrenci bilgileri "students.json" adlı bir JSON dosyasında saklanır.
- Uygulama, temel doğrulama ve filtreleme işlevselliği sağlar.

İhtiyacınıza göre özelleştirip genişletebilirsiniz.
