/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,jsx}'],
  theme: {
    extend: {
      colors: {
        brand: {
          50: '#f2f8ff',
          100: '#dceeff',
          200: '#b5dcff',
          300: '#7ec1ff',
          400: '#409aff',
          500: '#1476ff',
          600: '#0058f0',
          700: '#0048d2',
          800: '#073da8',
          900: '#0c357f',
        },
        ink: '#0f172a',
      },
      fontFamily: {
        sans: ['Manrope', 'ui-sans-serif', 'system-ui', 'sans-serif'],
        display: ['Space Grotesk', 'Manrope', 'ui-sans-serif', 'system-ui', 'sans-serif'],
      },
      boxShadow: {
        soft: '0 10px 30px -15px rgba(2, 6, 23, 0.25)',
        glow: '0 0 0 1px rgba(20, 118, 255, 0.18), 0 15px 40px -18px rgba(20, 118, 255, 0.35)',
      },
      backgroundImage: {
        'grid-light': 'linear-gradient(rgba(15,23,42,0.06) 1px, transparent 1px), linear-gradient(90deg, rgba(15,23,42,0.06) 1px, transparent 1px)',
      },
    },
  },
  plugins: [],
}

