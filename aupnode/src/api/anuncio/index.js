import { Router } from 'express'
import { middleware as query, Schema } from 'querymen'
import { middleware as body } from 'bodymen'
import { master, token } from '../../services/passport'
import { create, index, show, update, destroy, indexMyAnuncios, addFavorite, delFavorite, userFavorites, authenticatedIndex, indexOwner } from './controller'
import { schema } from './model'
export Anuncio, { schema }
from './model'

const router = new Router()
const { contenido, ownerId } = schema.tree

const anunciosSchema = new Schema({
    contenido: {
        type: [String],
        paths: ['contenido']
    },
    ownerId: {
        type: [String],
        paths: [ownerId]
    }
})

/**
 * @api {post} /anuncios Create anuncio
 * @apiName CreateAnuncio
 * @apiGroup Anuncio
 * @apiPermission master
 * @apiParam {String} access_token master access token.
 * @apiParam contenido Anuncio's contenido.
 * @apiParam ownerId Anuncio's ownerId.
 * @apiSuccess {Object} anuncio Anuncio's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Anuncio not found.
 * @apiError 401 master access only.
 */
router.post('/',
    token({ required: true }),
    body({ contenido }),
    create)

/**
 * @api {get} /anuncios Retrieve anuncios
 * @apiName RetrieveAnuncios
 * @apiGroup Anuncio
 * @apiUse listParams
 * @apiSuccess {Number} count Total amount of anuncios.
 * @apiSuccess {Object[]} rows List of anuncios.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 */
router.get('/',
    master(),
    query(anunciosSchema),
    index)

/**
 * @api {get} /anuncios Retrieve anuncios when user is authenticated
 * @apiName RetrieveAnunciosAuth
 * @apiGroup Anuncio
 * @apiUse listParams
 * @apiSuccess {Number} count Total amount of anuncios.
 * @apiSuccess {Object[]} rows List of anuncios.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 */
router.get('/auth',
    token({ required: true }),
    query(anunciosSchema),
    authenticatedIndex)

/**
 * @api {get} /anuncios Retrieve mine anuncios
 * @apiName RetrieveMineAnuncios
 * @apiGroup Anuncio
 * @apiUse listParams
 * @apiSuccess {Number} count Total amount of anuncios.
 * @apiSuccess {Object[]} rows List of anuncios.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 */
router.get('/mine',
    token({ required: true }),
    query(anunciosSchema),
    indexMyAnuncios)

/**
 * @api {get} /anuncios/:email Retrieve anuncios of one User
 * @apiName RetrieveMineAnuncios
 * @apiGroup Anuncio
 * @apiUse listParams
 * @apiSuccess {Number} count Total amount of anuncios.
 * @apiSuccess {Object[]} rows List of anuncios.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 */
router.get('/:email',
    master(),
    query(anunciosSchema),
    indexOwner)

/**
 * @api {get} /anuncios Retrieve mine anuncios
 * @apiName RetrieveMineAnuncios
 * @apiGroup Anuncio
 * @apiUse listParams
 * @apiSuccess {Number} count Total amount of anuncios.
 * @apiSuccess {Object[]} rows List of anuncios.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 */
router.get('/favs/tal',
    token({ required: true }),
    query(anunciosSchema),
    userFavorites)

/**
 * @api {get} /anuncios/:id Retrieve anuncio
 * @apiName RetrieveAnuncio
 * @apiGroup Anuncio
 * @apiSuccess {Object} anuncio Anuncio's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Anuncio not found.
 */
router.get('/tal/:id',
    query(anunciosSchema),
    show)

/**
 * @api {put} /anuncios/:id Update anuncio
 * @apiName UpdateAnuncio
 * @apiGroup Anuncio
 * @apiPermission master
 * @apiParam {String} access_token master access token.
 * @apiParam contenido Anuncio's contenido.
 * @apiParam ownerId Anuncio's ownerId.
 * @apiSuccess {Object} anuncio Anuncio's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Anuncio not found.
 * @apiError 401 master access only.
 */
router.put('/:id',
    master(),
    body({ contenido, ownerId }),
    update)

/**
 * @api {delete} /anuncios/:id Delete anuncio
 * @apiName DeleteAnuncio
 * @apiGroup Anuncio
 * @apiPermission master
 * @apiParam {String} access_token master access token.
 * @apiSuccess (Success 204) 204 No Content.
 * @apiError 404 Anuncio not found.
 * @apiError 401 master access only.
 */
router.delete('/:id',
    token({ required: true }),
    destroy)

/**
 * @api {post} /anuncios/fav/:id Add a anuncio as favorite
 * @apiName AddFavAnuncio
 * @apiGroup Anuncio
 * @apiPermission user
 * @apiParam {String} access_token user access token.
 * @apiSuccess {Object} user Users's data.
 * @apiError 401 user access only.
 */
router.post('/fav/:id',
    token({ required: true }),
    addFavorite)

/**
 * @api {delete} /anuncios/fav/:id Delete a anuncio as a favorite
 * @apiName DeleteFavAnuncio
 * @apiGroup Anuncio
 * @apiPermission user
 * @apiParam {String} access_token user access token.
 * @apiSuccess (Success 204) 204 No Content.
 * @apiError 401 user access only.
 */
router.delete('/fav/:id',
    token({ required: true }),
    delFavorite)

export default router