fetch("http://localhost:8080/api/menu")
    .then(function (data) {
        return data.json();
    }).then(function (menus) {
    let menuhtml = document.getElementById("menu");
    for (const menu of menus) {
        console.log(menu.parent);
        if (menu.parent === null) {
            let listItem = document.createElement("li");
            let link = document.createElement("a");
            link.href = menu.url; // Cần thay đổi href tương ứng với dữ liệu trong API
            link.innerHTML = menu.name;
            listItem.appendChild(link);
            menuhtml.appendChild(listItem);
            appendData(menus, menu, listItem);
        }
    }
});


function appendData(menus, menu, parentElement) {
    let submenu = document.createElement("ul");
    for (var menuInside of menus) {
        console.log(menuInside);
        if (menuInside.parent !== null && menuInside.parent.id_menu === menu.id_menu) {
            let listItem = document.createElement("li");
            let link = document.createElement("a");
            link.href = menuInside.url;
            link.innerHTML = menuInside.name;
            listItem.appendChild(link);
            submenu.appendChild(listItem);
            appendData(menus, menuInside, listItem);
        }
    }
    if (submenu.childNodes.length > 0) {
        parentElement.appendChild(submenu);
    }
}

