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
    // generateBooks();


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

    function borrowBook(readerId, copyId, bookId) {
        $.ajax({
            url: "http://localhost:8080//v1/library/createBorrow",
            method: 'POST',
            processData: false,
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: JSON.stringify({
                reader_id: readerId,
                copy_id: copyId
            }),
            success: function() {
                generateReaders();
                generateBooks(readerId);
                generateAvCopies('http://localhost:8080/v1/library/getAvailableCopies?book_id=' + bookId, readerId);
                generateBorrowed('http://localhost:8080/v1/library/getBorrowedBooks?reader_id=' + readerId);
            }
        })
    }

    function generateBorrowed(urlBorrowed) {
        borrowedTable = $("#datatable_borrowed_row").DataTable({
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
            var urlBorrowed = 'http://localhost:8080/v1/library/getBorrowedBooks?reader_id='+data.readerId;
            returnBook(urlReturnBook, urlBorrowed);
        } );
    }

    function generateAvCopies(urlAvCopies, readerId) {
        var avCopiesTable = $("#datatable_copies_row").DataTable({
            destroy: true,
            ajax: {
                url: urlAvCopies,
                dataSrc: ''
            },
            rowId: 'id',
            searching: false,
            paging: false,
            columns: [
                {data : "id"},
                {data : "status"},
                {data : "bookId"},
                {
                    targets: -1,
                    data: null,
                    defaultContent: "<button>Borrow</button>"
                }
            ]
        });
        $("#datatable_copies_row tbody").on( 'click', 'button', function () {
            var data = avCopiesTable.row($(this).parents('tr')).data();
            borrowBook(readerId, data.id, data.bookId);
        });
    }

    function generateBooks(readerId) {
        const requestUrl = apiRoot + 'getBooks';
        var booksTable = $("#datatable_books_row").DataTable( {
            // retrieve: true,
            destroy: true,
            pageLength: 5,
            // processing: true,
            // serverSide: true,
            ajax: {
                url: requestUrl,
                dataSrc: ''
            },
            rowId: 'id',
            columns: [
                {data : "id"},
                {data : "title"},
                {data : "author"},
                {data : "totalCopiesInLibrary"},
                {data : "copiesAvailable"},
                {
                    targets: -1,
                    data: null,
                    defaultContent: "<button>Show</button>"
                }
            ]
        });

        $("#datatable_books_row tbody").on( 'click', 'button', function () {
            var data = booksTable.row($(this).parents('tr')).data();
            var urlAvCopies = 'http://localhost:8080/v1/library/getAvailableCopies?book_id='+data.id;
            generateAvCopies(urlAvCopies, readerId);
        } );
    }

    function generateReaders() {
        const requestUrl = apiRoot + 'getReaders';
        var readersTable = $("#datatable_readers_row").DataTable( {
            // retrieve: true,
            destroy: true,
            pageLength: 5,
            // processing: true,
            // serverSide: true,
            ajax: {
                url: requestUrl,
                dataSrc: ''
            },
            rowId: 'id',
            columns: [
                {data : "id"},
                {data : "firstname"},
                {data : "lastname"},
                {data : "totalBorrowedBooks"},
                {data : "currentlyBorrowedBooks"},{
                    targets: -1,
                    data: null,
                    defaultContent: "<button id='btn1'> Books </button>"
                },
                {
                    targets: -1,
                    data: null,
                    defaultContent: "<button id='btn2'> Borrows </button>"
                }
            ]
            });

        $("#datatable_readers_row tbody").on( 'click', '#btn2', function () {
            var data = readersTable.row($(this).parents('tr')).data();
            // var id = readersTable.row($(this).parents('tr')).id();
            var urlBorrowed = 'http://localhost:8080/v1/library/getBorrowedBooks?reader_id='+data.id;
            // alert("Requesting borrowed books data from: " + url);
            generateBorrowed(urlBorrowed);
        } );

        $("#datatable_readers_row tbody").on( 'click', '#btn1', function () {
            var data = readersTable.row($(this).parents('tr')).data();
            // var id = readersTable.row($(this).parents('tr')).id();
            generateBooks(data.id);
            // alert("Requesting borrowed books data from: " + url);
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
