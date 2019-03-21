import { success, notFound, authorOrAdmin } from '../../services/response/'
import { Comentario } from '.'
import { Anuncio } from '../anuncio'

var idComentario;
export const create = async({ user, bodymen: { body } }, res, next) => {
    await Comentario.create({...body, userId: user })
        .then((comentario) => {
            idComentario = comentario.view(true).id;
            comentario.view(true);
        })
        .then(success(res, 201))
        .catch(next)

    await Anuncio.findByIdAndUpdate(body.anuncioId, { $addToSet: { comentarios: idComentario } })
        .then((comentario) => comentario.view(true))
        .then(success(res, 200))
        .catch(next)
}

export const index = ({ querymen: { query, select, cursor } }, res, next) =>
    Comentario.count(query)
    .then(count => Comentario.find(query, select, cursor)
        .then((comentarios) => ({
            count,
            rows: comentarios.map((comentario) => comentario.view())
        }))
    )
    .then(success(res))
    .catch(next)

export const indexAnuncio = ({ params, querymen: { query, select, cursor } }, res, next) =>
    Comentario
    .find({ anuncioId: params.id })
    .populate('userId', 'name picture role email')
    .then((comentarios) => ({
        count: comentarios.length,
        rows: comentarios.map((comentario) => comentario.view(true))
    }))
    .then(success(res))
    .catch(next)

export const show = ({ params }, res, next) =>
    Comentario.findById(params.id)
    .then(notFound(res))
    .then((comentario) => comentario ? comentario.view() : null)
    .then(success(res))
    .catch(next)

var idAnuncio;
export const destroy = async({ user, params }, res, next) => {
    await Comentario.findById(params.id)
        .then(notFound(res))
        .then(authorOrAdmin(res, user, 'userId'))
        .then((comentario) => {
            idAnuncio = comentario.anuncioId
            comentario ? comentario.remove() : null
        })
        .then(success(res, 204))
        .catch(next)

    await Anuncio.findByIdAndUpdate(idAnuncio, { $pull: { comentarios: params.id } })
        .then(success(res, 204))
        .catch(next)
}