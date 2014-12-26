var GetRemoteController = {
    view: {
        showMessage: function () {}
    },
    getRemote: function () {
        $http.get('http://jsonip.com', {
            p1: 'p1Value'
        }, function (response) {

            console.log(response);
            this.view.showMessage(response);

        }, function () {

            this.view.showMessage("Erro");
        });
    },

    postRemote: function () {
        $http.post('http://posttestserver.com/post.php', {
            p1: 'p1Value'
        }, function (response) {
            
            console.log(response);
            this.view.showMessage(response);
            
        }, function () {
        
        });
    }
};