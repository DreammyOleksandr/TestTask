'use client'

import { useState, useEffect, use } from 'react'
import Link from 'next/link'
import { MetricValue } from '@/app/interfaces/MetricValue'

export default function Details({ params }: { params: Promise<{ id: string }> }) {
  const resolvedParams = use(params)
  const [metricValue, setMetricValue] = useState<MetricValue | null>(null)
  const [history, setHistory] = useState<MetricValue[]>([])

  useEffect(() => {
    const fetchMetricValue = async () => {
      try {
        const response = await fetch(`http://localhost:8080/metricvalues/${resolvedParams.id}`)
        const data = await response.json()
        setMetricValue(data)
        console.log(data)

        const historyResponse = await fetch(
          `http://localhost:8080/metricvalues/history/${resolvedParams.id}`
        )
        if (historyResponse.status === 204) {
          setHistory([])
        } else {
          const historyData = await historyResponse.json()
          setHistory(historyData)
        }
      } catch (error) {
        console.error('Error fetching data:', error)
      }
    }

    fetchMetricValue()
  }, [resolvedParams.id])

  if (!metricValue) {
    return <div className='container mt-5'>Loading...</div>
  }

  return (
    <div className='container mt-5'>
      <h1 className='mb-4'>Metric Value Details</h1>
      <div className='card'>
        <div className='card-body'>
          <h5 className='card-title mb-3 h3'>{metricValue.metricType.name}</h5>
          <ul className='list-group list-group-flush'>
            <li className='list-group-item'>
              <strong>Value:</strong> {metricValue.value}
            </li>
            <li className='list-group-item'>
              <strong>Recorded Date:</strong> {new Date(metricValue.recordedDate).toLocaleString()}
            </li>
            <li className='list-group-item'>
              <strong>Metric Type:</strong> {metricValue.metricType.type}
            </li>
            <li className='list-group-item'>
              <strong>Normalized Name:</strong> {metricValue.metricType.normalizedName}
            </li>
          </ul>
        </div>
      </div>
      <div className='row mt-5'>
        <div className='card-body col-sm-6'>
          <h5 className='card-title mb-3 h4'>History</h5>
          <table className='table'>
            <thead>
              <tr>
                <th className='col-sm-4'>Value</th>
                <th className='col-sm-4'>Metric Name</th>
                <th className='col-sm-4'>Data Type</th>
              </tr>
            </thead>
            <tbody>
              {history.map((item) => (
                <tr key={item.id}>
                  <td>{item.value}</td>
                  <td>{item.metricType.name}</td>
                  <td>{item.metricType.type}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
        <div className='card-body'>
          <h5 className='card-title mb-3 h4'>Related Metric Values</h5>
          <table className='table'>
            <thead>
              <tr>
                <th className='col-sm-4'>Value</th>
                <th className='col-sm-4'>Metric Name</th>
                <th className='col-sm-4'>Data Type</th>
              </tr>
            </thead>
            <tbody>
              {metricValue.relatedMetricValues.map((item) => (
                <tr key={item.id}>
                  <td>{item.value}</td>
                  <td>{item.metricType.name}</td>
                  <td>{item.metricType.type}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
      <Link href='/' className='btn btn-primary mt-3 d-inline-block'>
        Back to Dashboard
      </Link>
    </div>
  )
}
