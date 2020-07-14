  <h1 align="center">All My Food <br><br>
  <img src="./Img/app_logo.png" width="150px">
  </h1>
  <p align="center">
    <a href="https://github.com/UCASV/entrega-de-proyecto-pdm-0120-editorial/releases/tag/v0.1.0">
      <img src="https://img.shields.io/badge/pre%20release-v0.1.0-blue"/>
    <a/>
    <a href="https://github.com/Walter26/foodAPI">
      <img src="https://img.shields.io/badge/npm-foodAPI-orange"/>
    </a>
    <img src="https://img.shields.io/badge/android--min-api%2019-lightgrey"/>
    <img src="https://img.shields.io/badge/android--target-api%2029-lightgrey"/>
    <img src="https://img.shields.io/badge/license-MIT-brightgreen"/>
  </p>
  
## Summary
[Tablero](#tablero-de-equipo)<br>
[API](#trabajo-de-api)<br>
[App](#trabajo-de-la-app)<br>

<hr>

## Tablero de equipo
[Notion.so](https://www.notion.so/Food-App-65fa094f2eb24290aebd059e03d679fa)

## API
La apliación está alimentada por la API foodAPI. En un overview, se conecta a una BD en MongoAtlas, y su deploy se encuentra en heroku.

## Rutas
**Main** https://food-api-wrmh.herokuapp.com/

| Method | Path                                                                   | Action                                                                 |
|--------|------------------------------------------------------------------------|------------------------------------------------------------------------|
| GET    | /                                                                      | Devuelve todos los usuarios registrados                                |
| GET    | /user?username&password                                                | Login                                                                  |
| POST   | /user/:username/:fullname/:password/:email/:userImage                  | Registra al usuario                                                    |
| GET    | /list?username                                                         | Obtiene todas las listas almacenadas de un usuario                     |
| POST   | /list/:username/:name/:desc/:elements                                  | Crea una lista para el usuario indicado                                |
| PUT    | /list/:_id/:username/:name/:desc/:elements                             | Actualiza la lista por su _id                                          |
| GET    | /recipe                                                                | Obtiene todas las recetas con privacidad pública                       |
| GET    | /recipe?author                                                        | Obtiene todas las recetas de un usuario                                |
| POST   | /recipe/:author/:title/:desc/:ingredients/:steps/:privacy/:recipeImage | Crea una receta, la privacidad depende de la configuración del usuario |

Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon"> www.flaticon.com</a>
