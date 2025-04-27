/*
----------------------------------------------------------------

Como está actualmente da un error de CORS porque noticias.rss es un archivo local.
Utilizé un servidor local de python con el comando:
python3 -m http.server
para comprobar que funcione

----------------------------------------------------------------
 */


document.addEventListener('DOMContentLoaded', function() {
    // Obtiene el RSS de noticias
    //La URL debería ser reemplazada por la del servidor
    fetch('../rss/noticias.rss', {
        //Headers para CORS. En un navegador moderno no hay realmente manera de evitar usar un servidor local
        //porque mode: no-cors no permite cargar el contenido
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/rss+xml'
        }
    })
        .then(res => res.text())
        .then(str => (new window.DOMParser()).parseFromString(str, "text/xml"))
        .then(data => {
            const items = data.querySelectorAll("item");
            const container = document.querySelector('.grupo-tarjetas');
            const meses = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"];

            items.forEach(item => {
                const title = item.querySelector('title').textContent;
                const description = item.querySelector('description').textContent;
                const link = item.querySelector('link').textContent;
                const pubDate = item.querySelector('pubDate').textContent;
                const fecha = new Date(pubDate);
                const dia = fecha.getDate();
                const mes = meses[fecha.getMonth()];
                const anio = fecha.getFullYear();

                // Crea el artículo
                const article = document.createElement('article');
                article.className = "tarjeta noticia-item";
                article.innerHTML = `
              <div class="noticia-fecha">
                <span class="dia">${dia}</span>
                <span class="mes">${mes}</span>
                <span class="anio">${anio}</span>
              </div>
              <div class="noticia-texto">
                <h3>${title}</h3>
                <p>${description}</p>
                <a href="${link}" class="read-more">Leer más</a>
              </div>
            `;
                container.appendChild(article);
            });
        })
        .catch(err => console.error("Error al cargar o procesar el RSS:", err));
});