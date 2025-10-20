<#import "layout.ftlh" as main>
<@main.layout ; spring>
    <style>

        .gradient-custom-3 {
            /* fallback for old browsers */
            background:#251e73;

            /* Chrome 10-25, Safari 5.1-6 */
            background: -webkit-linear-gradient(to right, rgba(40, 60, 40, 0.5), rgba(143, 211, 244, 0.5));

            /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
            background: -webkit-linear-gradient(to right, rgba(40, 60, 40, 0.5), rgba(143, 211, 244, 0.5));
        }

        .gradient-custom-4 {
            /* fallback for old browsers */
            background: -webkit-linear-gradient(to right, rgba(40, 60, 40, 0.5), rgba(143, 211, 244, 0.5));


            /* Chrome 10-25, Safari 5.1-6 */
            background: -webkit-linear-gradient(to right, rgba(40, 60, 40, 0.5), rgba(143, 211, 244, 0.5));

            /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
            background: -webkit-linear-gradient(to right, rgba(40, 60, 40, 0.5), rgba(143, 211, 244, 0.5));
        }
    </style>
    <section class="vh-100 bg-image"
             style="background-image: url('https://mdbcdn.b-cdn.net/img/Photos/new-templates/search-box/img4.webp');">
        <div class="mask d-flex align-items-center h-100 gradient-custom-3">
            <div class="container h-100">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col-12 col-md-9 col-lg-7 col-xl-6">
                        <div class="card" style="border-radius: 15px;">
                            <div class="card-body p-5">
                                <h2 class="text-uppercase text-center mb-5">Редактировать профиль</h2>

                                <form action="/auth/profile/edit" method="post" enctype="multipart/form-data">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                                <div class="form-outline mb-4">
                                        <label class="form-label">Имя</label>
                                        <@spring.formInput "editProfileDTO.name" 'class="form-control form-control-lg"' />
                                        <@spring.showErrors "<br>" "error text-danger" />
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label">Фамилия</label>
                                        <@spring.formInput "editProfileDTO.surname" 'class="form-control form-control-lg"' />
                                        <@spring.showErrors "<br>" "error text-danger" />
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label">Возраст</label>
                                        <@spring.formInput "editProfileDTO.age" 'type="number" class="form-control form-control-lg"' />
                                        <@spring.showErrors "<br>" "error text-danger" />
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label">Пароль</label>
                                        <@spring.formInput "editProfileDTO.password" 'type="password" class="form-control form-control-lg"' />
                                        <@spring.showErrors "<br>" "error text-danger" />
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label">Номер телефона</label>
                                        <@spring.formInput "editProfileDTO.phoneNumber" 'class="form-control form-control-lg"' />
                                        <@spring.showErrors "<br>" "error text-danger" />
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label">Тип аккаунта</label>
                                        <@spring.formSingleSelect "editProfileDTO.accountType" {"1":"Открытый","2":"Закрытый"} 'class="form-control form-control-lg"' />
                                        <@spring.showErrors "<br>" "error text-danger" />
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="avatarFile">Аватар (Загрузить фото):</label>
                                        <input type="file" id="avatarFile" name="avatarFile" class="form-control form-control-lg" />
                                        <@spring.showErrors "<br>" "error text-danger" />
                                    </div>

                                    <div class="d-flex justify-content-center">
                                        <button type="submit" class="btn btn-success btn-block btn-lg gradient-custom-4 text-body">
                                            Сохранить
                                        </button>
                                    </div>
                                </form>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

</@main.layout>
