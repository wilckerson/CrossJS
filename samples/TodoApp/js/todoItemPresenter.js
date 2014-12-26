var todoItemNativeView = {

    //UIMethods
    setTitleText: function (value) {},
    setDeleteButtonVisibility: function (isVisible) {},
    setSaveButtonEnable: function (isEnable) {},
    showConfirmDelete: function () {},

    //Description Methods
    getDescriptionText: function () {},
    setDescriptionText: function (value) {},

    //NavigationMethods
    loadTodoListPresenter: function () {}
};

var todoItemPresenter = {

    itemIndex: undefined,

    fromNative_onLoad: function () {

        todoItemNativeView.setSaveButtonEnable(false);
        
        if (!todoItemPresenter.itemIndex) {
            todoItemNativeView.setTitleText("New item");
            todoItemNativeView.setDeleteButtonVisibility(false);
        } else {
            todoItemNativeView.setTitleText("Edit item");
            todoItemNativeView.setDeleteButtonVisibility(true);

            //Populate item description
            var item = todoListPresenter.items[todoItemPresenter.itemIndex];
            todoItemNativeView.setDescriptionText(item.description);
        }
    },

    fromNative_onChangeDescriptionText: function () {

        var text = todoItemNativeView.getDescriptionText();
        
        if (text && text.length > 0) {
            todoItemNativeView.setSaveButtonEnable(true);
        } else {
            todoItemNativeView.setSaveButtonEnable(false);
        }
    },

    fromNative_onClickSave: function () {
        var description = todoItemNativeView.getDescriptionText();

        if (description) {
            if (!todoItemPresenter.itemIndex) //New Item
            {
                var item = {
                    description: description,
                    done: false
                };
                todoListPresenter.items.push(item);

            } else { //Edit Item

                todoListPresenter.items[todoItemPresenter.itemIndex].description = description;
            }

            todoItemNativeView.loadTodoListPresenter();
        }
    },

    fromNative_onClickDeleteButton: function () {

        todoItemNativeView.showConfirmDelete();
    },

    fromNative_onConfirmDelete: function () {
        todoListPresenter.items.splice(todoItemPresenter.itemIndex, 1);
        todoItemNativeView.loadTodoListPresenter();
    },
};