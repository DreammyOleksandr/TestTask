'use client'

import { useEffect, useState } from 'react'
import { useRouter, useParams } from 'next/navigation'
import { fetchMetricValues } from '../../../utils/api'
import { MetricValue } from '@/app/interfaces/MetricValue'

export default function EditMetricValue() {
  const router = useRouter()
  const { id } = useParams()
  const [metricValue, setMetricValue] = useState<MetricValue | null>(null)
  const [metricValues, setMetricValues] = useState<MetricValue[]>([])
  const [selectedRelations, setSelectedRelations] = useState<number[]>([])
  const [value, setValue] = useState<string>('')

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/metricvalues/${id}`)
        if (!response.ok) throw new Error('Failed to fetch metric value')
        const data: MetricValue = await response.json()
        setMetricValue(data)
        setValue(data.value)
        setSelectedRelations(data.relatedMetricValues.map((rel: MetricValue) => rel.id))

        const allRelations = await fetchMetricValues()
        setMetricValues(allRelations)
      } catch (error) {
        console.error('Error fetching metric value:', error)
      }
    }
    fetchData()
  }, [id])

  const handleCheckboxChange = (relationId: number) => {
    setSelectedRelations((prev) =>
      prev.includes(relationId) ? prev.filter((id) => id !== relationId) : [...prev, relationId]
    )
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()

    if (!metricValue) return

    const body = {
      value,
      relationIds: selectedRelations.length > 0 ? selectedRelations : [0],
    }

    try {
      const response = await fetch(`http://localhost:8080/metricvalues/${id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(body),
      })
      if (!response.ok) throw new Error('Failed to update metric value')
      console.log('Metric value updated successfully')
      router.push('/')
    } catch (error) {
      console.error('Error updating metric value:', error)
    }
  }

  return (
    <div className='container mt-5'>
      <h1>Edit Metric Value</h1>
      {metricValue ? (
        <form onSubmit={handleSubmit}>
          <div className='mb-3'>
            <label className='form-label'>Value</label>
            <input
              type='text'
              className='form-control'
              value={value}
              onChange={(e) => setValue(e.target.value)}
              required
            />
          </div>
          <div className='mb-3'>
            <label className='form-label'>Relations</label>
            <table className='table table-striped'>
              <thead>
                <tr>
                  <th>#</th>
                  <th>Value</th>
                  <th>Recorded Date</th>
                  <th>Include</th>
                </tr>
              </thead>
              <tbody>
                {metricValues
                  .filter((rel) => rel.metricType.id !== metricValue.metricType.id)
                  .map((relation, index) => (
                    <tr key={relation.id}>
                      <th scope='row'>{index + 1}</th>
                      <td>{relation.value}</td>
                      <td>{new Date(relation.recordedDate).toLocaleString()}</td>
                      <td>
                        <input
                          type='checkbox'
                          className='form-check-input'
                          id={`relation-${relation.id}`}
                          checked={selectedRelations.includes(relation.id)}
                          onChange={() => handleCheckboxChange(relation.id)}
                        />
                      </td>
                    </tr>
                  ))}
              </tbody>
            </table>
          </div>
          <button type='submit' className='btn btn-primary'>
            Save Changes
          </button>
        </form>
      ) : (
        <p>Loading...</p>
      )}
    </div>
  )
}
