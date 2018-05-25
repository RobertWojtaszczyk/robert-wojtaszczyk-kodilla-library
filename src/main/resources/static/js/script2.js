$(document).ready(function() {
    const $booksContainer = $("#data-books-container");
    const apiRoot = 'http://localhost:8080/v1/library/';

    const borrowsContainer = document.getElementById("data-borrows-container");
    // var borrowsContainer = $("#data-borrows-container");

    function handleBorrowsRender(data) {
        var htmlString = "";
        data.forEach(function (borrow) {
            htmlString = "<p>" + borrow.title + ":" + borrow.author + "</p>";
            borrowsContainer.insertAdjacentHTML('beforeend',htmlString);
        })
    }

    function handleShowBorrowedBooksRequest() {
        /*var parentEl = $(this).parents('[data-task-id]');
        var taskId = parentEl.attr('data-task-id');
        var requestUrl = apiRoot + 'deleteTask';

        $.ajax({
          url: requestUrl + '/?' + $.param({
            taskId: taskId
          }),
          method: 'DELETE',
          success: function() {
            parentEl.slideUp(400, function() { parentEl.remove(); });
          }
        })*/
        // alert("borrowed books");
        const requestUrl = apiRoot + 'getBooksToReturnByReader';
        var readerId = 1;
        $.ajax({
            url: requestUrl + '/?' + $.param({
                reader_id: readerId
            }),
            method: 'GET',
            contentType: "application/json",
            success: handleBorrowsRender
        })

        // $("#data-borrows-container").load();
    }





    $booksContainer.on('click',"#show-borrowed-books", handleShowBorrowedBooksRequest);
});