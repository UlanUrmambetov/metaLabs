<#import "layout.ftlh" as main>

<@main.layout>
    <h1>Личный кабинет</h1>

    <#if user??>
        Привет, ${user.name}!

    <#-- Аватарка -->
        <#if user.avatar?? && user.avatar?has_content>
            <img src="${user.avatar}" alt="Аватар" style="width:100px; height:100px; object-fit:cover;">

        <#else>
            <img src="/images/default-avatar.png"
                 alt="Аватар по умолчанию"
                 style="width:100px; height:100px; object-fit:cover;">
        </#if>


        <p>Фамилия: ${user.surname!"Не указана"}</p>
        <p>Возраст: ${user.age!"Не указан"}</p>
        <p>Email: ${user.email!"Не указан"}</p>
        <p>Номер телефона: ${user.phoneNumber!"Не указан"}</p>
    <#else>
        <p>Привет, гость!</p>
    </#if>
</@main.layout>
