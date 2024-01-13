<%@include file="common/header.jspf" %>
<title>Manage Your Todos</title>
</head>
<%@include file="common/navigation.jspf" %>
<body>
<div class="container">

    <h1>Enter Todo Details</h1>

    <%--@elvariable id="todo" type="com.ride.amt.todo.Todo"--%>
    <form:form method="post" modelAttribute="todo">

        <fieldset class="mb-3">
            <form:label path="description">Description</form:label>
            <form:input type="text" path="description" required="required"/>
            <form:errors path="description" cssClass="text-warning"/>
        </fieldset>

        <fieldset class="mb-3">
            <form:label path="targetDate">Target Date</form:label>
            <form:input type="text" path="targetDate" required="required"/>
            <form:errors path="targetDate" cssClass="text-warning"/>
        </fieldset>

        <form:input type="hidden" path="id"/>

        <form:input type="hidden" path="done"/>

        <input type="submit" class="btn btn-success"/>

    </form:form>

</div>
<%@include file="common/footer.jspf" %>
<script type="text/javascript">
    $('#targetDate').datepicker({
        format: 'yyyy-mm-dd'
    });
</script>
