var SumController = {

    view: {
        getNum1: function(){ console.log("getNum1 - Não implementado");},
        getNum2: function(){console.log("getNum2 - Não implementado");},
        showMessage: function(msg){console.log("showMessage - Não implementado");}
    },
    calculate: function(){
        console.log('Clicou no botão.');
        
        var n1 = this.view.getNum1();
        var n2 = this.view.getNum2();
         if(!n1 && n1 != 0 )
        {
            this.view.showMessage('Informe o primeiro numero.');
            return;
        }
        
        if(!n2 && n2 != 0)
        {
            this.view.showMessage('Informe o segundo numero.');
            return;
        }
        
        var result = n1 * n2;
        this.view.showMessage(n1 + ' * ' + n2 + ' = ' + result);
    }
};