const restAjaxUrl = "users/restaurants/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: restAjaxUrl,
    updateTable: function () {
        $.get(restAjaxUrl, updateTableByData);
    }
}




// $(document).ready(function () {
$(function () {
    makeEditable({
        "columns": [
            {
                "data": "registered",
                "render": function (date, type, row) {
                    if (type === "display") {
                        return date.substring(0, 10);
                    }
                    return date;
                }
            },
            {
                "data": "name"
            },
            {
                "data": "menu"
            },
            {
                "data": "sumOfVotes"
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderVoteBtn
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ]
    });
});

function renderVoteBtn(data, type, row) {
    if (type === "display") {
        return `<a onclick='voteRow(${row.id});'><span class='fa fa-bookmark-o'></span></a>`;
    }
}
function voteRow(id) {
    if (confirm(i18n['common.confirm'])) {
        $.ajax({
            url: ctx.ajaxUrl + id + "/vote",
            type: "POST"
        }).done(function () {
            ctx.updateTable();
            successNoty("common.voted");
        });
    }
}
