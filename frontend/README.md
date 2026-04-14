# Frontend · lab12026p

Frontend del sistema bancario construido con React y Vite.

## Scripts

```bash
npm install
npm run dev
npm run build
npm run preview
```

## Variable de entorno

El frontend consume la API usando la variable:

```bash
VITE_API_BASE_URL
```

Ejemplo local:

```bash
VITE_API_BASE_URL=http://localhost:8080
```

## Despliegue en Vercel

El proyecto ya quedó listo para desplegarse en Vercel.

Configuración recomendada en Vercel:

- Root Directory: `frontend`
- Framework Preset: `Vite`
- Build Command: `npm run build`
- Output Directory: `dist`

Variable de entorno requerida en Vercel:

- `VITE_API_BASE_URL`: URL pública del backend desplegado

Además, se agregó `vercel.json` para que las rutas de React Router redirijan correctamente a `index.html`.
