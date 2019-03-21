import { Router } from 'express'
import { middleware as query } from 'querymen'
import { middleware as body, Schema } from 'bodymen'
import { master, token } from '../../services/passport'
import { create, index, show, destroy, indexAnuncio } from './controller'
import { schema } from './model'
export Comentario, { schema }
from './model'

const router = new Router()
const { userId, anuncioId, contenido } = schema.tree

const comentariosSchema = new Schema({
    userId: {
        type: [String],
        paths: [userId]
    },
    anuncioId: {
        type: [String],
        paths: [anuncioId]
    },
    contenido: {
        type: [String],
        paths: ['contenido']
    }
})

/**
 * @api {post} /comentarios Create comentario
 * @apiName CreateComentario
 * @apiGroup Comentario
 * @apiParam userId Comentario's userId.
 * @apiParam anuncioId Comentario's anuncioId.
 * @apiParam contenido Comentario's contenido.
 * @apiSuccess {Object} comentario Comentario's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Comentario not found.
 */
router.post('/',
    token({ required: true }),
    body({ anuncioId, contenido }),
    create)

/**
 * @api {get} /comentarios Retrieve comentarios
 * @apiName RetrieveComentarios
 * @apiGroup Comentario
 * @apiPermission master
 * @apiParam {String} access_token master access token.
 * @apiUse listParams
 * @apiSuccess {Number} count Total amount of comentarios.
 * @apiSuccess {Object[]} rows List of comentarios.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 401 master access only.
 */
router.get('/',
    master(),
    query(),
    index)

/**
 * @api {get} /comentarios Retrieve comentarios
 * @apiName RetrieveComentarios
 * @apiGroup Comentario
 * @apiPermission master
 * @apiParam {String} access_token master access token.
 * @apiUse listParams
 * @apiSuccess {Number} count Total amount of comentarios.
 * @apiSuccess {Object[]} rows List of comentarios.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 401 master access only.
 */
router.get('/anuncio/:id',
    master(),
    query(comentariosSchema),
    indexAnuncio)

/**
 * AÑADIR MÉTODO LISTAR COMENTARIOS ONE ANUNCIO
 */

/**
 * @api {get} /comentarios/:id Retrieve comentario
 * @apiName RetrieveComentario
 * @apiGroup Comentario
 * @apiSuccess {Object} comentario Comentario's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Comentario not found.
 */
router.get('/:id',
    show)

/**
 * @api {delete} /Comentarios/:id Delete comentario
 * @apiName DeleteComentario
 * @apiGroup Comentario
 * @apiSuccess (Success 204) 204 No Content.
 * @apiError 404 Comentario not found.
 */
router.delete('/:id',
    token({ required: true }),
    destroy)

export default router