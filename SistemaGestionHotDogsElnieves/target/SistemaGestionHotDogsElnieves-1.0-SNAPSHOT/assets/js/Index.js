function borrarSesion() {
    sessionStorage.clear();
    localStorage.clear();
}

window.onload = borrarSesion;

