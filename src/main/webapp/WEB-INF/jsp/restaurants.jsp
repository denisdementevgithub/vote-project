<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>

<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron pt-4">
    <div class="container">
        <h3 class="text-center">Рестораны</h3>
        <sec:authorize access="hasRole('ADMIN')">
        <button class="btn btn-primary" onclick="add()">
            <span class="fa fa-plus"></span>
            Добавить
        </button>
        </sec:authorize>
        <table class="table table-striped" id="datatable">
            <thead>
            <tr>
                <th>Дата голосования</th>
                <th>Имя ресторана</th>
                <th>Меню</th>
                <th>Голосов</th>
                <th>Голосовать</th>
                <sec:authorize access="hasRole('ADMIN')">
                    <th>Редактировать ресторан</th>
                    <th>Удалить</th>
                </sec:authorize>



            </tr>
            </thead>
        </table>
    </div>
</div>

<div class="modal fade" tabindex="-1" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Добавить ресторан</h4>
                <button type="button" class="close" data-dismiss="modal" onclick="closeNoty()">&times;</button>
            </div>
            <div class="modal-body">
                <form id="detailsForm">
                    <input type="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="name" class="col-form-label">Имя ресторана</label>
                        <input type="text" class="form-control" id="name" name="name"
                               placeholder="Имя ресторана">
                    </div>

                    <div class="form-group">
                        <label for="menu" class="col-form-label">Меню</label>
                        <input type="text" class="form-control" id="menu" name="menu"
                               placeholder="Меню">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="closeNoty()">
                    <span class="fa fa-close"></span>
                    Отменить
                </button>
                <button type="button" class="btn btn-primary" onclick="save()">
                    <span class="fa fa-check"></span>
                    Сохранить
                </button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>

<sec:authorize access="hasRole('ADMIN')">
    <script src="resources/js/topjava.admin.restaurant.js" defer></script>
</sec:authorize>
<sec:authorize access="hasRole('USER')">
    <script src="resources/js/topjava.restaurant.js" defer></script>
</sec:authorize>
<script src="resources/js/topjava.common.js" defer></script>

</html>