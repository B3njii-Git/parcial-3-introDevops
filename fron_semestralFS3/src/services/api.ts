import { Alerta, Usuario, ZonaMonitoreo, Historial, Reporte, ReporteBackend } from '../types';

const API_BASE_URL = `http://${window.location.hostname}:9090`; 

const getAuthHeaders = () => {
  const token = localStorage.getItem('vsol_token');
  return {
    'Content-Type': 'application/json',
    ...(token ? { 'Authorization': `Bearer ${token}` } : {})
  };
};

// --- MOCK DATA ---
const mockReportes: ReporteBackend[] = [
  { id: "1", descripcion: "Humo avistado cerca del cerro San Cristóbal", latitud: -33.425, longitud: -70.633, urlImagen: "", urlVideo: "", estado: "PENDIENTE", fechaReporte: new Date().toISOString() },
  { id: "2", descripcion: "Fuego en pastizales sector norte", latitud: -33.395, longitud: -70.650, urlImagen: "", urlVideo: "", estado: "EN_PROCESO", fechaReporte: new Date(Date.now() - 86400000).toISOString() }
];

const mockAlertas: Alerta[] = [
  { id: 101, tipoAlerta: "INCENDIO_FORESTAL", mensaje: "Incendio detectado por sensor térmico", severidad: "CRITICAL", fechaCreacion: new Date().toISOString() },
  { id: 102, tipoAlerta: "FOCO_CALOR", mensaje: "Aumento inusual de temperatura en zona 4", severidad: "HIGH", fechaCreacion: new Date(Date.now() - 3600000).toISOString() }
];

const mockZonas: ZonaMonitoreo[] = [
  { id: 1, nombreZona: "Sector Bosque Nativo", latitud: -33.42, longitud: -70.62, nivelRiesgo: "ALTO", brigadaActiva: true },
  { id: 2, nombreZona: "Perímetro Urbano", latitud: -33.45, longitud: -70.66, nivelRiesgo: "MEDIO", brigadaActiva: false },
  { id: 3, nombreZona: "Reserva Nacional", latitud: -33.50, longitud: -70.55, nivelRiesgo: "BAJO", brigadaActiva: true }
];

const mockHistorial: Historial[] = [
  { id: 1, ubicación: "Cerro Manquehue", causaProbable: "Desconocida", fechaInicio: new Date(Date.now() - 172800000).toISOString(), fechaFin: new Date(Date.now() - 86400000).toISOString(), hectareasAfectadas: 15 },
  { id: 2, ubicación: "Quebrada de Macul", causaProbable: "Fogata mal apagada", fechaInicio: new Date(Date.now() - 500000000).toISOString(), fechaFin: new Date(Date.now() - 400000000).toISOString(), hectareasAfectadas: 5 }
];

// --- REPORTES ---
export const obtenerReportes = async (): Promise<ReporteBackend[]> => {
  return Promise.resolve(mockReportes);
};

export const crearReporte = async (nuevoReporte: ReporteBackend): Promise<ReporteBackend> => {
  const reporte = { ...nuevoReporte, id: Math.random().toString() };
  mockReportes.push(reporte);
  return Promise.resolve(reporte);
};

export const eliminarReporte = async (id: number | string): Promise<void> => {
  return Promise.resolve();
};

export const actualizarReporte = async (reporte: ReporteBackend): Promise<ReporteBackend> => {
  return Promise.resolve(reporte);
};

// --- ALERTAS E HISTORIAL ---
export const finalizarAlertaYCrearHistorial = async (
  alertaId: string | number, 
  ubicacion: string, 
  causa: string, 
  hectareas: number,
  fechaInicioIncidente: string
): Promise<void> => {
  mockHistorial.push({
    id: Math.random(),
    ubicación: ubicacion,
    causaProbable: causa,
    fechaInicio: new Date(fechaInicioIncidente).toISOString(),
    fechaFin: new Date().toISOString(),
    hectareasAfectadas: hectareas
  });
  return Promise.resolve();
};

// --- OBJETO API ---
export const api = {
  // USUARIOS: Login real conectando a AWS
  login: async (email: string, password: string): Promise<any> => {
    const response = await fetch(`${API_BASE_URL}/usuarios/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email, password }),
    });
    if (!response.ok) throw new Error('Credenciales inválidas');
    
    const data = await response.json();
    if (data.token) {
      localStorage.setItem('vsol_token', data.token);
    }
    return data;
  },

  // ALERTAS (MOCK)
  getAlertas: async (): Promise<Alerta[]> => {
    return Promise.resolve(mockAlertas);
  },

  // MONITOREO (MOCK)
  getZonas: async (): Promise<ZonaMonitoreo[]> => {
    return Promise.resolve(mockZonas);
  },

  // HISTORIAL (MOCK)
  getHistorial: async (): Promise<Historial[]> => {
    return Promise.resolve(mockHistorial);
  },

  // REPORTES (MOCK)
  getReportes: async (): Promise<ReporteBackend[]> => {
    return Promise.resolve(mockReportes);
  }
};
