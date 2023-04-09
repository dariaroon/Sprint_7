<br />
<div align="center">
<h3 align="center">Sprint 7</h3>

  <p align="center">
    Тестирования сервиса Яндекс.Самокат
    <br />
</div>
<details>
  <summary>Содержание</summary>
  <ol>
    <li>
      <a href="#about-the-project">О проекте</a>
      <ul>
        <li><a href="#built-with">Используемые технологии</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Запуск проекта</a>
      <ul>
        <li><a href="#prerequisites">Настройка</a></li>
        <li><a href="#installation">Запуск</a></li>
      </ul>
    </li>
    <li><a href="#contact">Контакты</a></li>
  </ol>
</details>

<a id="about-the-project"></a>
## О проекте
Проект предназначин для запуска автотестов по тестированию сервиса <a href="https://qa-scooter.praktikum-services.ru"><strong>Яндекс.Самокат</strong></a>.
Тесты содержат следующие сценарии:
* Тестирование создание курьера
* Тестирование логин курьера
* Тестирование создание заказа
* Тестирование списка заказов

<a id="built-with"></a>
### Используемые технологии
* Java 11
* Maven 3.8.1
* JUnit 4.13.2
* Allure 2.21.0

<a id="getting-started"></a>
## Запуск проекта

<a id="prerequisites"></a>
### Настройка

Перед запуском проекта необходимо установить следующие компонетнты:
* Java 11
* Maven 3
* Allure

<a id="installation"></a>
### Запуск
  ```sh
  mvn clean test
  mvn allure:serve
  ```
<a id="contact"></a>
## Контакты

Дарья Роон - [github](https://github.com/dariaroon)

Ссылка на проект: [sprint_7](https://github.com/dariaroon/Sprint_7)
