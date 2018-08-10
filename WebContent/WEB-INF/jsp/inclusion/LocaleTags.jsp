<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.locale" var="loc" />

<!-- For navigation bar -->
<fmt:message bundle="${loc}" key="local.main.local.button.ru" var="ru_button" />
<fmt:message bundle="${loc}" key="local.main.local.button.en" var="en_button" />
<fmt:message bundle="${loc}" key="local.main.login.button" var="login_button" />
<fmt:message bundle="${loc}" key="local.navbar.link.home" var="nav_link_home" />
<fmt:message bundle="${loc}" key="local.navbar.link.user.page" var="nav_link_users" />
<fmt:message bundle="${loc}" key="local.navbar.link.user.page.title" var="nav_link_users_title" />
<fmt:message bundle="${loc}" key="local.navbar.link.about" var="nav_link_about" />
<fmt:message bundle="${loc}" key="local.navbar.link.contacts" var="nav_link_contacts" />
<fmt:message bundle="${loc}" key="local.navbar.link.staff" var="nav_link_staff" />
<fmt:message bundle="${loc}" key="local.navbar.link.staff.slogan" var="nav_link_slogan" />
<fmt:message bundle="${loc}" key="local.navbar.link.staff.header" var="nav_link_header" />
<fmt:message bundle="${loc}" key="local.navbar.link.user.button.edit" var="nav_link_edit" />
<fmt:message bundle="${loc}" key="local.navbar.link.user.button.exit" var="nav_link_exit" />
<fmt:message bundle="${loc}" key="local.navbar.link.language" var="nav_link_language" />

<!-- Login and registration page -->
<fmt:message bundle="${loc}" key="local.loginpage.title" var="loginpage_title" />
<fmt:message bundle="${loc}" key="local.loginpage.panel.heading.title" var="loginpage_panel_title" />
<fmt:message bundle="${loc}" key="local.loginpage.panel.login.placeholder" var="login_placeholder" />
<fmt:message bundle="${loc}" key="local.loginpage.panel.password.placeholder" var="password_placeholder" />
<fmt:message bundle="${loc}" key="local.loginpage.panel.checkbox" var="loginpage_checkbox" />
<fmt:message bundle="${loc}" key="local.loginpage.panel.button" var="loginpage_button" />
<fmt:message bundle="${loc}" key="local.loginpage.panel.link.registration" var="loginpage_link_registration" />
<fmt:message bundle="${loc}" key="local.name" var="name" />
<fmt:message bundle="${loc}" key="local.surname" var="surname" />
<fmt:message bundle="${loc}" key="local.registrationpage.button" var="registration_button" />
<fmt:message bundle="${loc}" key="local.registrationpage.error" var="registration_error" />

<!-- Error messages for login page -->
<fmt:message bundle="${loc}" key="null.login.password.message.error" var="null_login_password" />
<fmt:message bundle="${loc}" key="empty.login.password.message.error" var="empty_login_password" />
<fmt:message bundle="${loc}" key="wrong.login.message.error" var="wrong_login" />
<fmt:message bundle="${loc}" key="wrong.password.message.error" var="wrong_password" />
<fmt:message bundle="${loc}" key="null.hash.password.message.error" var="hash_password_problem" />