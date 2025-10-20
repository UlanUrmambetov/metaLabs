<#import "spring.ftl" as spring>
<#macro layout>
    <!DOCTYPE html>
    <head>
        <meta charset="UTF-8" />
        <title></title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-LN+7fdVzj6u52u30Kp6M/trliBMCMKTyK833zpbD+pXdCLuTusPj697FH4R/5mcr" crossorigin="anonymous">
        <style>
            body {
                background-color: #96a1c3;
            }
            header h1 {
                color: #251e73;
            }
            nav a {
                font-weight: 600;
            }
        </style>
    </head>
    <body>
    <header class="bg-light p-3 mb-4">
        <div class="container">
            <h1><@spring.message "layout.platform" /> <@spring.message "layout.vacancies" /></h1>
            <nav>
                <a href="/">
                    <#if user??>
                        <a href="/auth/profile" class="me-3"><@spring.message "layout.profile" /></a>
                        <a href="/auth/profile/edit" class="me-3"><@spring.message "layout.edit" /> <@spring.message "layout.profile" /></a>

                        <a href="/logout"><@spring.message "layout.logout" /></a>
                    <#else>
                        <a href="/auth/login"><@spring.message "layout.login" /></a>
                    </#if>
                </a>

            </nav>
        </div>
    </header>

    <main class="container">
        <#nested spring>
    </main>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/js/bootstrap.bundle.min.js" integrity="sha384-..." crossorigin="anonymous"></script>
    </body>
    </html>
</#macro>
