import { Router } from 'express'
import { middleware as query } from 'querymen'
import { middleware as body } from 'bodymen'
import { master } from '../../services/passport'
import { create, index, show, update, destroy } from './controller'
import { schema } from './model'
export Anuncio, { schema }
from './model'

const router = new Router()
const { contenido, ownerId } = schema.tree

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
    master(),
    body({ contenido, ownerId }),
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
    query(),
    index)

/**
 * @api {get} /anuncios/:id Retrieve anuncio
 * @apiName RetrieveAnuncio
 * @apiGroup Anuncio
 * @apiSuccess {Object} anuncio Anuncio's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Anuncio not found.
 */
router.get('/:id',
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
    master(),
    destroy)

export default router