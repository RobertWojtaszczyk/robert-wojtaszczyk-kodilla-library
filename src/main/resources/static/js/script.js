$(document).ready(function() {
	const $booksContainer = $("#data-books-container");
    const apiRoot = 'http://localhost:8080/v1/library/';
    const datatableReadersTemplate = $("#data-datatable-readers-template").children()[0];
    const $readersContainer = $("#datatable_readers_row");
    const borrowsContainer = document.getElementById("data-borrows-container");
    // var borrowsContainer = $("#data-borrows-container");
    var availableReaders = {};
    var borrowedTable;

    generateReaders();


    function returnBook(urlReturnBook, urlBorrowed) {
        $.ajax({
            url: urlReturnBook,
            method: 'PUT',
            success: function() {
                generateBorrowed(urlBorrowed);
                generateReaders();
            }
        })
    }

    function generateBorrowed(urlBorrowed) {
        borrowedTable = $("#datatable_borrowed_row").DataTable({
            // retrieve: true,
            destroy: true,
            ajax: {
                url: urlBorrowed,
                dataSrc: ''
            },
            rowId: 'readerId',
            searching: false,
            paging: false,
            columns: [
                {data : "id"},
                {data : "title"},
                {data : "author"},
                {data : "borrowDate"},
                {data : "returnDate"},
                {data : "overdue"},
                {data : "readerId"},
                {
                    targets: -1,
                    data: null,
                    defaultContent: "<button>Return</button>"
                }
            ]
        });


        $("#datatable_borrowed_row tbody").on( 'click', 'button', function () {
            var data = borrowedTable.row($(this).parents('tr')).data();
            var urlReturnBook = 'http://localhost:8080/v1/library/returnBook?borrow_id='+data.id;
            // var id = readersTable.row(this).id();
            var urlBorrowed = 'http://localhost:8080/v1/library/getBooksToReturnByReader?reader_id='+data.readerId;
            returnBook(urlReturnBook, urlBorrowed);
        } );

        /*$("#datatable_readers_row tbody").on( 'click', 'button', function () {
            var data = readersTable.row($(this).parents('tr')).data();
            alert( ":" + data.name + ':borrowed: '+ data.id + ' books, reader id=' + id);
        } );*/
    }

    function generateReaders() {
        const requestUrl = apiRoot + 'getReaders';
        var readersTable = $("#datatable_readers_row").DataTable( {
            // retrieve: true,
            destroy: true,
            // processing: true,
            // serverSide: true,
            ajax: {
                url: requestUrl,
                dataSrc: ''
            },
            rowId: 'id',
            columns: [
                {data : "id"},
                {data : "name"},
                {data : "surname"},
                {data : "totalBorrowedBooks"},
                {data : "currentlyBorrowedBooks"},
                {
                    targets: -1,
                    data: null,
                    defaultContent: "<button>Show</button>"
                }
            ]
            });

        $("#datatable_readers_row tbody").on( 'click', 'button', function () {
            var data = readersTable.row($(this).parents('tr')).data();
            // var id = readersTable.row($(this).parents('tr')).id();
            var urlBorrowed = 'http://localhost:8080/v1/library/getBooksToReturnByReader?reader_id='+data.id;
            // alert("Requesting borrowed books data from: " + url);
            generateBorrowed(urlBorrowed);
        } );


        /*$("#datatable_readers_row").on( 'click', 'tr', function () {
            var id = readersTable.row(this).id();
            alert( 'Clicked row id '+id );
        } );*/

    }

    /*$(document).ready(function() {
        var table = $('#example').DataTable( {
            "ajax": "data/arrays.txt",
            "columnDefs": [ {
                "targets": -1,
                "data": null,
                "defaultContent": "<button>Click!</button>"
            } ]
        } );

        $('#example tbody').on( 'click', 'button', function () {
            var data = table.row( $(this).parents('tr') ).data();
            alert( data[0] +"'s salary is: "+ data[ 5 ] );
        } );
    } );*/


});
