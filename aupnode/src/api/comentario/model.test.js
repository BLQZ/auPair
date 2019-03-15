import { Comentario } from '.'

let comentario

beforeEach(async () => {
  comentario = await Comentario.create({ userId: 'test', anuncioId: 'test', contenido: 'test' })
})

describe('view', () => {
  it('returns simple view', () => {
    const view = comentario.view()
    expect(typeof view).toBe('object')
    expect(view.id).toBe(comentario.id)
    expect(view.userId).toBe(comentario.userId)
    expect(view.anuncioId).toBe(comentario.anuncioId)
    expect(view.contenido).toBe(comentario.contenido)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })

  it('returns full view', () => {
    const view = comentario.view(true)
    expect(typeof view).toBe('object')
    expect(view.id).toBe(comentario.id)
    expect(view.userId).toBe(comentario.userId)
    expect(view.anuncioId).toBe(comentario.anuncioId)
    expect(view.contenido).toBe(comentario.contenido)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })
})
